package tads;

import indice.ItemIndiceInvertido;

public class NoArvoreRN {
	private ItemIndiceInvertido elemento; // a palavra do elemento sera a chave
	private NoArvoreRN esquerda, direita, pai;
	private boolean cor; // 'false' eh cor preta, 'true' eh vermelho
	
	public NoArvoreRN (ItemIndiceInvertido elemento, NoArvoreRN esq, NoArvoreRN dir) {
		this.elemento = elemento;
		this.esquerda = esq;
		this.direita = dir;
		this.cor = true;
	}
	
	public NoArvoreRN (ItemIndiceInvertido elemento) {
		this (elemento, null, null);
	}

	public ItemIndiceInvertido getElemento() {
		return elemento;
	}

	public void setElemento(ItemIndiceInvertido elemento) {
		this.elemento = elemento;
	}

	public NoArvoreRN getEsquerda() {
		return esquerda;
	}

	public void setEsquerda(NoArvoreRN esquerda) {
		this.esquerda = esquerda;
	}

	public NoArvoreRN getDireita() {
		return direita;
	}

	public void setDireita(NoArvoreRN direita) {
		this.direita = direita;
	}

	public NoArvoreRN getPai() {
		return pai;
	}

	public void setPai(NoArvoreRN pai) {
		this.pai = pai;
	}

	public boolean getCor() {
		return cor;
	}

	public void setCor(boolean cor) {
		this.cor = cor;
	}

	public String getChave() {
		return this.elemento.getPalavra();
	}
}
