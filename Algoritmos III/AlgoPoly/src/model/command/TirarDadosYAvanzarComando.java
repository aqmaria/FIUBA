package model.command;

import model.juego.Jugador;

public class TirarDadosYAvanzarComando extends Comando{

	public static String nombreAccion = "Tirar dados";
	
	@Override
	public void ejecutar() {
		this.jugador.avanzar(this.jugador.tirarDados());
	}
	
	
	private Jugador jugador;

	public TirarDadosYAvanzarComando(Jugador jugadorNuevo) {
		super(nombreAccion);
		this.jugador = jugadorNuevo;
	}

	public String getNombre() {
		return nombre;
	}
}
