package view.eventos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.juego.Jugador;
import view.ContenedorPrincipal;

public class BotonComprarPropiedadEventHandler implements EventHandler<ActionEvent> {
	private ContenedorPrincipal contenedor;
	private Jugador jugador;
	private String nombreCompra;
	
	public BotonComprarPropiedadEventHandler(Jugador jugador, String nombreCompra, ContenedorPrincipal contenedor) {
		this.jugador = jugador;
		this.contenedor = contenedor;
		this.nombreCompra = nombreCompra;
	}
	
    @Override
    public void handle(ActionEvent actionEvent) {
        this.jugador.getAccionesPosibles().get(nombreCompra).ejecutar();
        
        this.contenedor.actualizar();
    }
}