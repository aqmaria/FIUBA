package model;

import model.juego.Dado;
import model.juego.TiradaDados;

public class TiradaSiempreIgualAValor extends TiradaDados {

	private int valor;

	@Override
	public int tirar() {
		return this.valor;
	}

	public TiradaSiempreIgualAValor(int valor) {
		super();
		this.valor = valor;
		this.dados.add(new Dado(1, 1));
		this.dados.add(new Dado(2, 2));//para que no repita turno por dados iguales
	}
	
}
