package tads;

import indice.IndiceInvertido;
import interfaces.Dicionario;

public class NoArvoreRN implements Dicionario {
	private IndiceInvertido elemento; // a palavra do elemento sera a chave
	private NoArvoreAvl esquerda, direita, pai;
	private boolean cor; // 'false' eh cor preta, 'true' eh vermelho
	
	public NoArvoreRN (IndiceInvertido elemento, NoArvoreAvl esq, NoArvoreAvl dir) {
		this.elemento = elemento;
		this.esquerda = esq;
		this.direita = dir;
		this.cor = false;
	}
	
	public NoArvoreRN (IndiceInvertido elemento) {
		this (elemento, null, null);
	}
	
	public void inserir(IndiceInvertido item) {
		
	}
	
	public IndiceInvertido remover(IndiceInvertido item) {
		
	}
	
	public IndiceInvertido buscar(IndiceInvertido item) {
		
	}
}
