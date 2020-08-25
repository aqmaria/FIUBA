package view.eventos;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import excepciones.BarrioDobleNoPuedeEdificarHotelSinHaberEdificadoMaximaCantidadDeCasasException;
import excepciones.BarrioDobleNoPuedeEdificarSiPropietarioTieneUnaDeLasDosPropiedadesException;
import excepciones.BarrioLimiteEdificacionesException;
import excepciones.JugadorEfectivoInsuficienteException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceDialog;
import model.casillero.propiedad.Barrio;
import model.command.Comando;
import model.command.EdificarComando;
import model.juego.Juego;
import model.juego.Jugador;
import view.ContenedorPrincipal;


public class BotonEdificarEventHandler implements EventHandler<ActionEvent>{
	
	private Juego juego;
	private ContenedorPrincipal contenedor;
	
	public BotonEdificarEventHandler(Juego juego , ContenedorPrincipal contenedor) {
		this.juego = juego;
		this.contenedor = contenedor;
	}

    @Override
    public void handle(ActionEvent actionEvent) {
        Jugador jugador = juego.getJugadorActual();
        Hashtable<String,Barrio> barrios = jugador.obtenerBarrios();
        List<String> opciones = new ArrayList<>();
        for (String nombreBarrio : barrios.keySet()) {
            opciones.add(nombreBarrio);
        }
        
        ChoiceDialog<String> dialogo = new ChoiceDialog<>("", opciones);
        dialogo.setTitle("Edificar");
        dialogo.setHeaderText("Elija en que barrio quiere edificar.");
        dialogo.setContentText("barrios disponibles:");

        Optional<String> resultado = dialogo.showAndWait();
        if (resultado.isPresent()) {
    		try {
    				Comando edificar = jugador.getAccionesPosibles().get(EdificarComando.prefijoAccion + resultado.get());
    	        	if( edificar ==null) new Alerta("NO PUEDE EDIFICAR", "Solo puede edificar antes de tirar los dados"); 
    	        	else edificar.ejecutar();
    		} catch (JugadorEfectivoInsuficienteException e1) {
    			new Alerta("NO PUEDE CONSTRUIR","No tiene suficiente dinero para construir");
    		} catch (BarrioLimiteEdificacionesException e2) {
    			new Alerta("NO PUEDE CONSTRUIR","Ya ha superado limite de edificaciones");
    		} catch (BarrioDobleNoPuedeEdificarSiPropietarioTieneUnaDeLasDosPropiedadesException e3) {
    			new Alerta("NO PUEDE CONSTRUIR","Necesita tener las dos zonas del barrio para poder construir");
    		} catch (BarrioDobleNoPuedeEdificarHotelSinHaberEdificadoMaximaCantidadDeCasasException e4) {
    			new Alerta("NO PUEDE CONSTRUIR","No puede seguir construyendo sin haber alcanzado la maxima capacidad de construcciones en zona sur y norte");
    		}
        }
        this.contenedor.actualizar();
    }

}
