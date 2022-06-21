package tads;

import indice.IndiceInvertido;
import interfaces.Dicionario;

public class NoArvore234 implements Dicionario { // t = 2
	private int numMaxChaves;
	private boolean folha;
	private IndiceInvertido[] chaves;
	private NoArvore234[] filhos;
	
	public NoArvore234() {
		this.chaves = new IndiceInvertido[3];
		this.filhos = new NoArvore234[4];
		this.folha = true;
		this.numMaxChaves = 3;
	}
	
	public void inserir(IndiceInvertido item) {
		
	}
	
	public IndiceInvertido remover(IndiceInvertido item) {
		
	}
	
	public IndiceInvertido buscar(IndiceInvertido item) {
		
	}
}
