package model;

import model.juego.Dado;
import model.juego.TiradaDados;

public class TiradaDosDadosSiempreNumerosIguales extends TiradaDados {

	public TiradaDosDadosSiempreNumerosIguales(int numero) {
		super();
		dados.add(new Dado(numero, numero));
		dados.add(new Dado(numero, numero));
	}

	@Override
	public void agregarDado(Dado dado) {
		return;
	}

	

}
