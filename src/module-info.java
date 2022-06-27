module analizadorSintacticoPro {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens root to javafx.graphics, javafx.fxml;
}
