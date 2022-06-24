package tads;
import indice.*;
import interfaces.Dicionario;

public class NoArvoreAvl  {
	private ItemIndiceInvertido elemento; // a palavra do elemento sera a chave
	private NoArvoreAvl esquerda, direita;
	private int altura;
	
	public NoArvoreAvl (ItemIndiceInvertido elemento, NoArvoreAvl esq, NoArvoreAvl dir) {
		this.elemento = elemento;
		this.esquerda = esq;
		this.direita = dir;
		this.altura = 0;
	}
	
	public NoArvoreAvl (ItemIndiceInvertido elemento) {
		this (elemento, null, null);
	}

	public NoArvoreAvl getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(NoArvoreAvl esquerda) {
		this.esquerda = esquerda;
	}

	public NoArvoreAvl getDireita() {
		return direita;
	}

	public void setDireita(NoArvoreAvl direita) {
		this.direita = direita;
	}

	public ItemIndiceInvertido getElemento() {
		return elemento;
	}

	public void setElemento(ItemIndiceInvertido elemento) {
		this.elemento = elemento;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}
}
