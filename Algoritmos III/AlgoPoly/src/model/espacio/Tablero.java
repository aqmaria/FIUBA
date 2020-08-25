package model.espacio;

import model.casillero.AvanceDinamico;
import model.casillero.Carcel;
import model.casillero.Casillero;
import model.casillero.ImpuestoAlLujo;
import model.casillero.Policia;
import model.casillero.Quini6;
import model.casillero.RetrocesoDinamico;
import model.casillero.Salida;
import model.casillero.propiedad.*;

public class Tablero {
	
	private static final int CANT_CASILLEROS = 20;
	private static final int POS_SALIDA = 0;
	private static final int POS_QUINI = 1;
	private static final int POS_BSASSUR = 2;
	private static final int POS_EDESUR = 3;
	private static final int POS_BSASNORTE = 4;
	private static final int POS_CARCEL = 5;
	private static final int POS_CORDOBASUR = 6;
	private static final int POS_AVANCE = 7;
	private static final int POS_SUBTE = 8;
	private static final int POS_CORDOBANORTE = 9;
	private static final int POS_IMPUESTO = 10;
	private static final int POS_SANTAFE = 11;
	private static final int POS_AYSA = 12;
	private static final int POS_SALTANORTE = 13;
	private static final int POS_SALTASUR = 14;
	private static final int POS_POLICIA = 15;
	private static final int POS_TRENES = 16;
	private static final int POS_NEUQUEN = 17;
	private static final int POS_RETROCESO = 18;
	private static final int POS_TUCUMAN = 19;
	
	
	private Casillero[] casillerosOrdenados;
	private GrupoBarrios[] gruposBarrios;
	private GrupoCompanias[] gruposCompanias;

	public Tablero() {
		this.casillerosOrdenados = new Casillero[CANT_CASILLEROS];
		casillerosOrdenados[POS_SALIDA] = new Salida(new Posicion(POS_SALIDA));
		casillerosOrdenados[POS_QUINI] = new Quini6(new Posicion(POS_QUINI));
		casillerosOrdenados[POS_BSASSUR] = new BuenosAiresSur(new Posicion(POS_BSASSUR));
		casillerosOrdenados[POS_EDESUR] = new Edesur(new Posicion(POS_EDESUR));
		casillerosOrdenados[POS_BSASNORTE] = new BuenosAiresNorte(new Posicion(POS_BSASNORTE));
		casillerosOrdenados[POS_CARCEL] = new Carcel(new Posicion(POS_CARCEL));
		casillerosOrdenados[POS_CORDOBASUR] = new CordobaSur(new Posicion(POS_CORDOBASUR));
		casillerosOrdenados[POS_AVANCE] = new AvanceDinamico(new Posicion(POS_AVANCE));
		casillerosOrdenados[POS_SUBTE] = new Subte(new Posicion(POS_SUBTE));
		casillerosOrdenados[POS_CORDOBANORTE] = new CordobaNorte(new Posicion(POS_CORDOBANORTE));
		casillerosOrdenados[POS_IMPUESTO] = new ImpuestoAlLujo(new Posicion(POS_IMPUESTO));
		casillerosOrdenados[POS_SANTAFE] = new SantaFe(new Posicion(POS_SANTAFE));
		casillerosOrdenados[POS_AYSA] = new Aysa(new Posicion(POS_AYSA));
		casillerosOrdenados[POS_SALTANORTE] = new SaltaNorte(new Posicion(POS_SALTANORTE));
		casillerosOrdenados[POS_SALTASUR] = new SaltaSur(new Posicion(POS_SALTASUR));
		casillerosOrdenados[POS_POLICIA] = new Policia(new Posicion(POS_POLICIA), casillerosOrdenados[POS_CARCEL].getPosicion());
		casillerosOrdenados[POS_TRENES] = new Tren(new Posicion(POS_TRENES));
		casillerosOrdenados[POS_NEUQUEN] = new Neuquen(new Posicion(POS_NEUQUEN));
		casillerosOrdenados[POS_RETROCESO] = new RetrocesoDinamico(new Posicion(POS_RETROCESO));
		casillerosOrdenados[POS_TUCUMAN] = new Tucuman(new Posicion(POS_TUCUMAN));
		
		gruposBarrios = new GrupoBarrios[6];
		gruposCompanias = new GrupoCompanias[2];
		
		GrupoBarrios grupoBsAs = new GrupoBarrios(); 
		
		
		grupoBsAs.agregar((Barrio)casillerosOrdenados[POS_BSASNORTE]);
		grupoBsAs.agregar((Barrio)casillerosOrdenados[POS_BSASSUR]);
		gruposBarrios[0] = grupoBsAs;
		
		GrupoBarrios grupoCordoba = new GrupoBarrios();
		grupoCordoba.agregar((Barrio)casillerosOrdenados[POS_CORDOBANORTE]);
		grupoCordoba.agregar((Barrio)casillerosOrdenados[POS_CORDOBASUR]);
		gruposBarrios[1] = grupoCordoba;
		
		GrupoBarrios grupoSantaFe =new GrupoBarrios();
		grupoSantaFe.agregar((Barrio)casillerosOrdenados[POS_SANTAFE]);
		gruposBarrios[2] = grupoSantaFe;
		
		GrupoBarrios grupoSalta = new GrupoBarrios();
		grupoSalta.agregar((Barrio)casillerosOrdenados[POS_SALTANORTE]);
		grupoSalta.agregar((Barrio)casillerosOrdenados[POS_SALTASUR]);
		gruposBarrios[3] = grupoSalta;
		
		GrupoBarrios grupoNeuquen =new GrupoBarrios();
		grupoNeuquen.agregar((Barrio)casillerosOrdenados[POS_NEUQUEN]);
		gruposBarrios[4] = grupoNeuquen;
		
		GrupoBarrios grupoTucuman =new GrupoBarrios();
		grupoTucuman.agregar((Barrio)casillerosOrdenados[POS_TUCUMAN]);
		gruposBarrios[5] = grupoTucuman;
		
		//Barrio quieroqueestoterminepronto = (Barrio)casillerosOrdenados[19]; jaja
		
		GrupoCompanias grupoTrenesSubte = new GrupoCompanias();
		grupoTrenesSubte.agregar((Compania)casillerosOrdenados[POS_TRENES]);
		grupoTrenesSubte.agregar((Compania)casillerosOrdenados[POS_SUBTE]);
		gruposCompanias[0] = grupoTrenesSubte;
		
		GrupoCompanias grupoAysaEdesur = new GrupoCompanias();
		grupoTrenesSubte.agregar((Compania)casillerosOrdenados[POS_AYSA]);
		grupoTrenesSubte.agregar((Compania)casillerosOrdenados[POS_EDESUR]);
		gruposCompanias[1] = grupoAysaEdesur;
	}

	public Casillero getCasilleroEnPosicion(Posicion posicion) {
		return casillerosOrdenados[posicion.getValor()];
	}

	public void agregarCasillero(Casillero casillero, Posicion posicion) {
		casillerosOrdenados[posicion.getValor()] = casillero;
	}
	
}
