package tads;

import indice.ItemIndiceInvertido;
import interfaces.Dicionario;

public class NoArvore234 { // t = 2
	private int numMaxChaves;
	private boolean folha;
	private ItemIndiceInvertido[] chaves;
	private NoArvore234[] filhos;
	
	public NoArvore234() {
		this.chaves = new ItemIndiceInvertido[3];
		this.filhos = new NoArvore234[4];
		this.folha = true;
		this.numMaxChaves = 3;
	}

	public int getNumMaxChaves() {
		return numMaxChaves;
	}

	public void setNumMaxChaves(int numMaxChaves) {
		this.numMaxChaves = numMaxChaves;
	}

	public boolean isFolha() {
		return folha;
	}

	public void setFolha(boolean folha) {
		this.folha = folha;
	}

	public ItemIndiceInvertido[] getChaves() {
		return chaves;
	}

	public void setChaves(ItemIndiceInvertido[] chaves) {
		this.chaves = chaves;
	}

	public NoArvore234[] getFilhos() {
		return filhos;
	}

	public void setFilhos(NoArvore234[] filhos) {
		this.filhos = filhos;
	}
}
