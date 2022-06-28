package tads;
import indice.*;

public class NoHash {
	private ItemIndiceInvertido item;
	
	private String chave;
	private int indice; // posicao na lista hash
	private NoHash proximoItem; // proximo item da posicao atual
	
	public NoHash(ItemIndiceInvertido item, NoHash proximoItem) {
		this.item = item;
		this.chave = this.item.getPalavra();
		this.proximoItem = proximoItem;
	}
	
	public NoHash (ItemIndiceInvertido item) {
		this (item, null);
	}

	public ItemIndiceInvertido getItem() {
		return item;
	}

	public void setItem(ItemIndiceInvertido item) {
		this.item = item;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public NoHash getProximoItem() {
		return proximoItem;
	}

	public void setProximoItem(NoHash proximoItem) {
		this.proximoItem = proximoItem;
	}

	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}
}
