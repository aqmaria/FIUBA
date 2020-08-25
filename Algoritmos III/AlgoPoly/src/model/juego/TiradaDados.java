package model.juego;

import java.util.ArrayList;
import java.util.LinkedList;

public class TiradaDados {
	
	protected LinkedList<Dado> dados;
	protected ArrayList<Integer> valoresUltimaTirada;
	
	public TiradaDados() {
		dados = new LinkedList<Dado>();
		valoresUltimaTirada = new ArrayList<Integer>();
	}
	
	public void agregarDado(Dado dado) {
		dados.add(dado);
	}

	public int tirar() {
		int tirada = 0;
		this.valoresUltimaTirada = new ArrayList<Integer>();
		
		for(Dado dado : dados) {
			int resultado = dado.tirar();
			this.valoresUltimaTirada.add(resultado);
			tirada = tirada + resultado;
			
		}
		return tirada;
	}
	
	public ArrayList<Integer> getValoresUltimaTirada(){
		return new ArrayList<Integer>(valoresUltimaTirada);
	}
	
}
