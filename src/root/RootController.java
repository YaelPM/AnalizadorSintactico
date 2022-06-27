package root;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.fxml.Initializable;

public class RootController implements Initializable {

	String DatosDeEntrada = "";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Generando datos de entrada
		DatosDeEntrada = "CREATE TABLE 'images' ( \n" + "  'idimages' in NOT_NULL AUTO_INCREMENT PRIMARY KEY ,\r\n"
				+ "  'type' varchar NOT_NULL ,\r\n" + "  'name' varchar NOT_NULL ,\r\n"
				+ "  'data' mediumblob NOT_NULL ,\r\n" + "  'userId' int NOT_NULL ,\r\n"
				+ "  FOREING KEY ( 'idimages' ) REFERENCES 'users' ( 'idUser' )\r\n" + ") ENGINE=InnoDB ;";
		System.out.println(DatosDeEntrada);
		String[] DatosDeEntradaDivididos = DatosDeEntrada.split("\\s+");
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(DatosDeEntradaDivididos));
		Stack<String> stack = LlenarDatosDeLaPila();
		AnalizadorSintactico analizador= new AnalizadorSintactico(stack, arrayList);
		analizador.checarDatos();
	}

	public Stack<String> LlenarDatosDeLaPila() {
		Stack<String> stack = new Stack<>();
		stack.push("RCrearTablas");
		stack.push("Tabla");
		return stack;
	}

}
