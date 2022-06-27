package root;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class RootController implements Initializable {

	String DatosDeEntrada = "";
	@FXML
    private Button button;

    @FXML
    private Text text1;

    @FXML
    private Text text2;

    @FXML
    private TextArea textArea;
    
    @FXML
    void buttonOnMouseClicked(MouseEvent event) {
    	
    	DatosDeEntrada= textArea.getText();
    	if(DatosDeEntrada.equals("")) {
    		System.out.println("no hay datos de entrada");
    	}else {
    		System.out.println(DatosDeEntrada);
    		String[] DatosDeEntradaDivididos = DatosDeEntrada.split("\\s+");
    		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(DatosDeEntradaDivididos));
    		Stack<String> stack = LlenarDatosDeLaPila();
    		AnalizadorSintactico analizador= new AnalizadorSintactico(stack, arrayList);
    		analizador.checarDatos();
    		if(analizador.getError().equals("")) {
    			text1.setText("Correcto");
    			text2.setText(analizador.getError());
    		}else {
    			text1.setText("INCORRECTO");
    			text2.setText(analizador.getError());
    		}
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Generando datos de entrada
		/*DatosDeEntrada = "CREATE TABLE 'images' ( \n" + "  'idimages' int NOT_NULL AUTO_INCREMENT PRIMARY KEY ,\r\n"
				+ "  'type' varchar NOT_NULL ,\r\n" + "  'name' varchar NOT_NULL ,\r\n"
				+ "  'data' mediumblob NOT_NULL ,\r\n" + "  'userId' int NOT_NULL ,\r\n"
				+ "  FOREING KEY ( 'idimages' ) REFERENCES 'users' ( 'idUser' )\r\n" + ") ENGINE=InnoDB ;";
		System.out.println(DatosDeEntrada);
		String[] DatosDeEntradaDivididos = DatosDeEntrada.split("\\s+");
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(DatosDeEntradaDivididos));
		Stack<String> stack = LlenarDatosDeLaPila();
		AnalizadorSintactico analizador= new AnalizadorSintactico(stack, arrayList);
		analizador.checarDatos();*/
	}

	public Stack<String> LlenarDatosDeLaPila() {
		Stack<String> stack = new Stack<>();
		stack.push("Tabla");
		return stack;
	}

}
