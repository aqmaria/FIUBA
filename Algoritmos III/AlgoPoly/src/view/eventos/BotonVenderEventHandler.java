package view.eventos;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceDialog;
import model.casillero.propiedad.Propiedad;
import model.command.Comando;
import model.command.VenderPropiedadComando;
import model.juego.Juego;
import model.juego.Jugador;
import view.ContenedorPrincipal;

public class BotonVenderEventHandler implements EventHandler<ActionEvent> {
	private Juego juego;
	private ContenedorPrincipal contenedor;
	
	public BotonVenderEventHandler(Juego juego , ContenedorPrincipal contenedor) {
		this.juego = juego;
		this.contenedor = contenedor;
	}
	
    @Override
    public void handle(ActionEvent actionEvent) {
        Jugador jugador = juego.getJugadorActual();
        Hashtable<String,Propiedad> propiedades = jugador.obtenerPropiedades();
        List<String> opciones = new ArrayList<>();
        for (String nombrePropiedad : propiedades.keySet()) {
            opciones.add(nombrePropiedad);
        }
        
        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Vender");
        dialogo.setHeaderText("Elija que propiedad quiere vender.");
        dialogo.setContentText("Propiedades disponibles:");

        Optional<String> resultado = dialogo.showAndWait();
        if (resultado.isPresent()) {
        	Comando comando = jugador.getAccionesPosibles().get(VenderPropiedadComando.prefijoAccion + resultado.get());
        	if(comando==null) new Alerta("NO PUEDE VENDER", "Solo puede vender antes de tirar los dados o cuando debe afrontar deuda"); 
        	else comando.ejecutar();
        }
        this.contenedor.actualizar();
    }
}
