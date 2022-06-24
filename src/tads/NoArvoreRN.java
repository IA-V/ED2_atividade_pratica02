package tads;

import indice.ItemIndiceInvertido;
import interfaces.Dicionario;

public class NoArvoreRN /*implements Dicionario*/ {
	private ItemIndiceInvertido elemento; // a palavra do elemento sera a chave
	private NoArvoreAvl esquerda, direita, pai;
	private boolean cor; // 'false' eh cor preta, 'true' eh vermelho
	
	public NoArvoreRN (ItemIndiceInvertido elemento, NoArvoreAvl esq, NoArvoreAvl dir) {
		this.elemento = elemento;
		this.esquerda = esq;
		this.direita = dir;
		this.cor = false;
	}
	
	public NoArvoreRN (ItemIndiceInvertido elemento) {
		this (elemento, null, null);
	}
	
	public void inserir(ItemIndiceInvertido item) {
		
	}
	
	/*public IndiceInvertido remover(IndiceInvertido item) {
		
	}
	
	public IndiceInvertido buscar(IndiceInvertido item) {
		
	}*/
}
