package root;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

public class AnalizadorSintactico {
	private Stack<String> stack = new Stack<>();
	private ArrayList<String> datosDeEntrada = new ArrayList<>();
	private String regex = "";
	private int aux = 0;
	private String error = "";

	public String getError() {
		return error;
	}

	public AnalizadorSintactico(Stack<String> stack, ArrayList<String> datosDeEntrada) {
		this.stack = stack;
		this.datosDeEntrada = datosDeEntrada;
	}
	/*
	 * public void peekAll(Stack<String> stackTest) {
	 * System.out.println("Peek All"); while (stack.isEmpty() == false) {
	 * System.out.println(stackTest.peek()); stackTest.pop(); } }
	 */

	public void checarDatos() {
		while (stack.isEmpty() == false) {
			try {
				System.out.println("Peek inicial: " + stack.peek());
				System.out.println("Apuntador: " + datosDeEntrada.get(0));
				// Si lo que hay arriba de la pila es terminal
				if (stack.peek().equals(datosDeEntrada.get(0))) {
					System.out.println(datosDeEntrada.get(0) + " is correct");
					stack.pop();
					datosDeEntrada.remove(0);
				} else {
					if (stack.peek().equals("NOT_NULL")){
						stack.pop();
						datosDeEntrada.remove(0);
					}
					// Si lo que hay arriba de la pila es no terminal

					// errores
					if (stack.peek().equals("CREATE") || stack.peek().equals("TABLE")
							|| stack.peek().equals("ENGINE=InnoDB") || stack.peek().equals("REFERENCES")
							|| stack.peek().equals("(") || stack.peek().equals(")") || stack.peek().equals(";")
							|| stack.peek().equals(",") || stack.peek().equals("AUTO_INCREMENT")
							|| stack.peek().equals("PRIMARY") || stack.peek().equals("KEY")) {
						System.out.println("Sintaxis incorrecta en: " + datosDeEntrada.get(0));
						stack.pop();
						error = datosDeEntrada.get(0);
						datosDeEntrada.clear();
					} else {

						// Tabla-> CrearTabla Nombre ParentesisApertura ColumnaInicial Columna
						// LlaveForanea ParentesisCierre Engine PuntoYComa

						if (stack.peek().equals("Tabla")) {
							stack.pop();
							stack.push("PuntoYComa");
							stack.push("Engine");
							stack.push("ParentesisCierre");
							stack.push("LlaveForanea");
							stack.push("Columna");
							stack.push("ColumnaInicial");
							stack.push("ParentesisApertura");
							stack.push("Nombre");
							stack.push("CrearTabla");
						}
						// RCrearTablas-> CrearTablas RCrearTablas | vacio
						if (stack.peek().equals("RCrearTablas")) {
							stack.pop();
							// stack.push("RCrearTablas");
							stack.push("CrearTablas");
						}
						// CrearTabla-> Create Table
						if (stack.peek().equals("CrearTabla")) {
							stack.pop();
							stack.push("Table");
							stack.push("Create");
						}
						// Create-> CREATE
						if (stack.peek().equals("Create")) {
							stack.pop();
							stack.push("CREATE");
							this.aux++;
						}
						// Table-> TABLE
						if (stack.peek().equals("Table")) {
							stack.pop();
							stack.push("TABLE");
						}
						// Nombre-> Comillas Letras RLetras Comillas
						if (stack.peek().equals("Nombre")) {
							regex = "\\'([a-zA-Z])+\\'";
							if (Pattern.matches(regex, datosDeEntrada.get(0))) {
								System.out.println(datosDeEntrada.get(0) + " is correct");
								stack.pop();
								datosDeEntrada.remove(0);
							} else {
								System.out.println(datosDeEntrada.get(0) + " is incorrect");
								error = datosDeEntrada.get(0);
								datosDeEntrada.clear();
							}
						}
						// ParentesisApertura-> (
						if (stack.peek().equals("ParentesisApertura")) {
							stack.pop();
							stack.push("(");
						}

						// ColumnaInicial-> Nombre Tipo IsNull IsAutoincremetal LlavePrimaria Coma
						if (stack.peek().equals("ColumnaInicial")) {
							stack.pop();
							stack.push("Coma");
							stack.push("LlavePrimaria");
							stack.push("IsAutoincremental");
							stack.push("IsNull");
							stack.push("Tipo");
							stack.push("Nombre");
						}
						// Columna-> Nombre Tipo IsNull Coma RestoDeColumnas
						if (stack.peek().equals("Columna")) {
							stack.pop();
							stack.push("RestoDeColumnas");
							stack.push("Coma");
							stack.push("IsNull");
							stack.push("Tipo");
							stack.push("Nombre");
						}
						// RestoDeColumnas-> Columna RestoDeColumnas | vacio
						if (stack.peek().equals("RestoDeColumnas")) {
							stack.pop();
							if (datosDeEntrada.get(0).equals("FOREING")) {
								stack.pop();
							} else {
								stack.push("RestoDeColumnas");
								stack.push("Columna");
							}
						}
						// Tipo-> bigint | binary | bit | blob | bool | boolean | char | character |
						// date | datetime | decimal | double | int | integer | json | linestring | long
						// | longblob | mediumblob | mediumint | text | time
						if (stack.peek().equals("Tipo")) {
							regex = "(bigint|varchar|binary|bit|blob|bool|boolean|char|character|date|datetime|decimal|double|int|integer|json|linestring|long|longblob|mediumblob|mediumint|text|time)";
							if (Pattern.matches(regex, datosDeEntrada.get(0))) {
								System.out.println(datosDeEntrada.get(0) + " is correct");
								stack.pop();
								datosDeEntrada.remove(0);
							} else {
								System.out.println(datosDeEntrada.get(0) + " is incorrect");
								error = datosDeEntrada.get(0);
								datosDeEntrada.clear();
							}
						}
						// IsNull -> NULL | NOT NULL | vacio
						if (stack.peek().equals("IsNull")) {
							regex = "(NULL|NOT_NULL)";
							System.out.println("\n peek: "+ stack.peek()+" Apuntador"+ datosDeEntrada.get(0) );
							if (Pattern.matches(regex, datosDeEntrada.get(0))) {
								System.out.println(datosDeEntrada.get(0) + " is correct");
								stack.pop();
								datosDeEntrada.remove(0);
							} else {
								System.out.println(datosDeEntrada.get(0) + " is incorrect");
								//error = datosDeEntrada.get(0);
								stack.pop();
							}
						}
						// IsAutoincremental-> AUTO_INCREMENT | vacio
						if (stack.peek().equals("IsAutoincremental")) {
							stack.pop();
							stack.push("AUTO_INCREMENT");
						}
						// LlavePrimaria-> PRIMARY KEY
						if (stack.peek().equals("LlavePrimaria")) {
							stack.pop();
							stack.push("KEY");
							stack.push("PRIMARY");
						}
						// Foreing-> F K
						if (stack.peek().equals("Foreing")) {
							stack.pop();
							stack.push("K");
							stack.push("F");
						}
						// F-> FOREING
						if (stack.peek().equals("F")) {
							stack.pop();
							stack.push("FOREING");
						}
						// K-> KEY
						if (stack.peek().equals("K")) {
							stack.pop();
							stack.push("KEY");
						}
						// Coma-> ,
						if (stack.peek().equals("Coma")) {
							stack.pop();
							stack.push(",");
						}
						// LlaveForanea-> Foreing ParentesisApertura Nombre ParentesisCierre References
						// Nombre ParentesisApertura Nombre ParensesisCierre | VACIO
						if (stack.peek().equals("LlaveForanea")) {
							stack.pop();
							stack.push("ParentesisCierre");
							stack.push("Nombre");
							stack.push("ParentesisApertura");
							stack.push("Nombre");
							stack.push("References");
							stack.push("ParentesisCierre");
							stack.push("Nombre");
							stack.push("ParentesisApertura");
							stack.push("Foreing");
						}
						// ParentesisCierre-> )
						if (stack.peek().equals("ParentesisCierre")) {
							stack.pop();
							stack.push(")");
						}
						// References-> REFERENCES
						if (stack.peek().equals("References")) {
							stack.pop();
							stack.push("REFERENCES");
						}
						// Engine-> ENGINE=InnoDB
						if (stack.peek().equals("Engine")) {
							stack.pop();
							stack.push("ENGINE=InnoDB");
						}
						// PuntoYComa-> ;
						if (stack.peek().equals("PuntoYComa")) {
							stack.pop();
							stack.push(";");
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Error");
				stack.clear();
				error= "faltan datos";
			}
			
		}
	}

}
