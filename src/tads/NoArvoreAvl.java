package tads;
import indice.*;
import interfaces.Dicionario;

public class NoArvoreAvl implements Dicionario {
	private IndiceInvertido elemento; // a palavra do elemento sera a chave
	private NoArvoreAvl esquerda, direita;
	private int altura;
	
	public NoArvoreAvl (IndiceInvertido elemento, NoArvoreAvl esq, NoArvoreAvl dir) {
		this.elemento = elemento;
		this.esquerda = esq;
		this.direita = dir;
		this.altura = 0;
	}
	
	public NoArvoreAvl (IndiceInvertido elemento) {
		this (elemento, null, null);
	}
	
	public void inserir(IndiceInvertido item) {
		
	}
	
	public IndiceInvertido remover(IndiceInvertido item) {
		
	}
	
	public IndiceInvertido buscar(IndiceInvertido item) {
		
	}
}
