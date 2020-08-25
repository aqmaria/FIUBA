package view.eventos;

import excepciones.JugadorEfectivoInsuficienteException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.command.PagarFianzaComando;
import model.juego.Juego;
import view.ContenedorPrincipal;

public class BotonPagarFianzaEventHandler implements EventHandler<ActionEvent> {
	private Juego juego;
	private ContenedorPrincipal contenedor;
	
	public BotonPagarFianzaEventHandler(Juego juego , ContenedorPrincipal contenedor) {
		this.juego = juego;
		this.contenedor = contenedor;
	}
	
	@Override
	public void handle(ActionEvent actionEvent) {
		try {
			juego.getJugadorActual().getAccionesPosibles().get(PagarFianzaComando.nombreAccion).ejecutar();
			this.contenedor.actualizar();

		} catch (JugadorEfectivoInsuficienteException e1) {
			new Alerta("NO PUEDE PAGAR FIANZA","No tiene suficiente dinero");
		}
		this.contenedor.actualizar();

	}
}
