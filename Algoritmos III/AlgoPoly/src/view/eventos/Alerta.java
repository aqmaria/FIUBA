package view.eventos;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerta {

	public Alerta(String titulo, String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Aviso");
		alert.setHeaderText(titulo);
		alert.setContentText(mensaje);
	    alert.show();
	}
	
}
