package model.juego;

import model.espacio.Posicion;

import java.util.ArrayList;
import java.util.Hashtable;

import model.casillero.propiedad.Barrio;
import model.casillero.propiedad.Compania;
import model.casillero.propiedad.Propiedad;
import model.command.Comando;
import model.command.EdificarComando;
import model.command.TerminarTurnoComando;
import model.command.TirarDadosYAvanzarComando;
import model.command.VenderPropiedadComando;

public class Jugador {
	
	private static final int TURNOS_MAX_DADOS_IGUALES = 1;
	private String nombre;
	private int efectivo;
	private Posicion posicion;
	
	private JugadorState estado;
	private boolean puedeMoverse;
	
	private Hashtable<String,Propiedad> propiedades;
	
	private Hashtable<String, Comando> accionesExtraInicioTurno;
	private int ultimaTiradaDados;
	private TiradaDados tirada;
	private Juego juego;
	private Hashtable<String, Comando> accionesPosibles;
	private int turnosConDadosIguales;

	public Jugador(String nombreNuevo, Juego juegoNuevo){
		this.juego = juegoNuevo;
		this.setNombre(nombreNuevo);
		this.setEfectivo(juegoNuevo.getEfectivoInicial());
		this.setPosicion(new Posicion(0));
		this.setEnCarcelState(new JugadorLiberadoState());
		this.ultimaTiradaDados = 0;
		this.propiedades = new Hashtable<String,Propiedad>();
		this.tirada = juego.getTiradaJuego();
		this.puedeMoverse = true;
		this.accionesPosibles = new Hashtable<String, Comando>();
		this.accionesExtraInicioTurno = new Hashtable<String, Comando>();
		this.turnosConDadosIguales = 0;
	}
	
	/*
	 * 	GETTERS Y SETTERS TRIVIALES
	 */
	
	public Posicion getPosicion() {
		
		return new Posicion(this.posicion.getValor());
	}
	
	private void setPosicion(Posicion posicion) {
		this.posicion = posicion;
	}

	public int getEfectivo() {
		return this.efectivo;
	}

	public void setEfectivo(int efectivo) {
		this.efectivo = efectivo;
	}
	
	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getUltimaTiradaDados() {
 		return this.ultimaTiradaDados;
 	}
	
	public void setUltimaTiradaDados(int num) {
 		this.ultimaTiradaDados = num;
 	}
	
	public boolean tieneBarrios() {
		if(obtenerBarrios().size() ==0) return false;
		return true;
	}
	
	
	/*
	 *	MOVIMIENTO 
	 */
	
	public void moverA(Posicion posicionNueva) {
		this.posicion = new Posicion(posicionNueva.getValor());
		this.juego.getCasilleroEnPosicion(this.posicion).colocarJugador(this);
	}
	
	public void avanzar(int cantPosiciones) {
		this.posicion.avanzar(cantPosiciones);
		this.juego.getCasilleroEnPosicion(this.posicion).colocarJugador(this);
	}
	
	public void retroceder(int cantCasilleros) {
		this.avanzar(-cantCasilleros);
	}
	
	public void impedirMovimiento() {
		TiradaDados tiradaNuevaSiempre0 = new TiradaDados();
		tiradaNuevaSiempre0.agregarDado(new Dado(0, 0));
		tiradaNuevaSiempre0.agregarDado(new Dado(0, 0));
		this.tirada = tiradaNuevaSiempre0;
		puedeMoverse = false;
	}
	
	public void permitirMovimiento() {
		this.tirada = juego.getTiradaJuego();
		puedeMoverse = true;
	}
	
	public boolean puedeMoverse() {
		return puedeMoverse;
	}

	/*
	 * 	DADOS
	 */
	
	public int tirarDados() {
		int tiradaDados = tirada.tirar();
		setUltimaTiradaDados(tiradaDados);
		
		this.accionesPosibles = new Hashtable<String, Comando>();
		Comando comando;
		
		if(this.dadosIguales() && turnosConDadosIguales < TURNOS_MAX_DADOS_IGUALES) {
			comando = new TirarDadosYAvanzarComando(this);
			turnosConDadosIguales++;
		}
		else {
			comando = new TerminarTurnoComando(this.juego);
			turnosConDadosIguales = 0;
		}
		
		accionesPosibles.put(comando.getNombre(), comando);
		
		return tiradaDados;
	}
	
	private boolean dadosIguales() {
		boolean iguales = true;
		ArrayList<Integer> valores = this.tirada.getValoresUltimaTirada();
		if(valores.size() == 0 || this.ultimaTiradaDados == 0) return false;
		
		int valorAnterior = valores.get(0);
		
		for(Integer valor : valores) {
			if(valor != valorAnterior)
				iguales = false;
		}
		
		return iguales;
	}

	/*
	 * 	CARCEL
	 */
	
	public void accionarCarcel() {
		estado.accionarCarcel(this);
	}

	public void setEnCarcelState(JugadorState estadoCarcelNuevo) {
		estado = estadoCarcelNuevo;
	}

	public void pagarFianza() {
		estado.pagarFianza(this, this.juego.getPrecioFianza());
	}

	/*
	 * 	EFECTIVO
	 */
	
	public void debitar(int monto) {
		this.efectivo -= monto;
	}

	public void acreditar(int monto) {
		this.efectivo += monto;		
	}
  
	
	public int getSumaPropiedades() {
 		return propiedades.size();
 	}
	
	

	/*
	 * 	PROPIEDADES
	 */
	
	public void agregarPropiedad(Propiedad propiedad) {
 		this.propiedades.put(propiedad.getNombre(), propiedad);
 	}

	public void quitarPropiedad(Propiedad propiedad) {
		propiedades.remove(propiedad.getNombre());
	}

	public Hashtable<String, Propiedad> obtenerPropiedades() {
		return new Hashtable<String, Propiedad>(propiedades);
	}
	
	public Hashtable<String, Barrio> obtenerBarrios() {
		Hashtable<String, Barrio> barrios = new Hashtable<String, Barrio>();
		for(String nombrePropiedad : propiedades.keySet()) {
			Propiedad propiedad = this.propiedades.get(nombrePropiedad);
			if(propiedad instanceof Barrio)
				barrios.put(propiedad.getNombre(), (Barrio)propiedad);
		}
		
		return barrios;
	}
	
	public Hashtable<String, Compania> obtenerCompanias() {
		Hashtable<String, Compania> barrios = new Hashtable<String, Compania>();
		for(String nombrePropiedad : propiedades.keySet()) {
			Propiedad propiedad = this.propiedades.get(nombrePropiedad);
			if(propiedad instanceof Compania)
				barrios.put(propiedad.getNombre(), (Compania)propiedad);
		}
		
		return barrios;
	}
	
	public Propiedad getPropiedad(String propiedadNombre ) {
		return this.propiedades.get(propiedadNombre);
	}

	public boolean puedePagar(int monto) {
		return monto <= this.efectivo;
	}
	
	/*
	 * 	ACCIONES
	 */

	public Hashtable<String, Comando> getAccionesPosibles() {
		if(efectivo >= 0) return new Hashtable<String, Comando>(accionesPosibles);
		if(this.propiedades.size() == 0) {
			this.juego.hacerPerder(this);
			return new Hashtable<String, Comando>();
		}
		
		Hashtable<String, Comando> ventaPropiedades = new Hashtable<String, Comando>();
		
		for(Propiedad propiedad : this.propiedades.values()) {
			Comando comando = new VenderPropiedadComando(propiedad);
			ventaPropiedades.put(comando.getNombre(), comando);
		}
		
		return ventaPropiedades;
	}

	public void agregarAccion(Comando comando) {
		accionesPosibles.put(comando.getNombre(), comando);
	}
	
	public void eliminarAccion(String nombreAccion) {
		accionesPosibles.remove(nombreAccion);
	}
	
	public void agregarAccionInicioTurno(Comando comando) {
		accionesExtraInicioTurno.put(comando.getNombre(), comando);
	}
	
	public void eliminarAccionInicioTurno(String nombreAccion) {
		accionesExtraInicioTurno.remove(nombreAccion);
	}
	
	/*
	 * 	TURNO
	 */
	
	
	
	public void terminarTurno() {
		this.accionesPosibles = new Hashtable<String, Comando>();
	}

	public void iniciarTurno() {
		this.accionesPosibles = new Hashtable<String, Comando>();
		
		for(Propiedad propiedad : this.propiedades.values()) {
			Comando comando = new VenderPropiedadComando(propiedad);
			accionesPosibles.put(comando.getNombre(), comando);
		}
		
		for(Barrio barrio : this.obtenerBarrios().values()) {
			Comando comando = new EdificarComando(barrio);
			accionesPosibles.put(comando.getNombre(), comando);
		}
		
		for(Comando accionExtra : accionesExtraInicioTurno.values())
			accionesPosibles.put(accionExtra.getNombre(), accionExtra);

		Comando tirarDadosYAvanzar = new TirarDadosYAvanzarComando(this);
		accionesPosibles.put(tirarDadosYAvanzar.getNombre(), tirarDadosYAvanzar);
	}

	public void setTirada(TiradaDados tiradaNueva) {
		this.tirada = tiradaNueva;
	}

	public ArrayList<Integer> getValoresUltimaTirada() {
		return new ArrayList<Integer>(this.tirada.getValoresUltimaTirada());
	}

	

	
}