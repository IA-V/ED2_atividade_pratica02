package interfaces;

import indice.ItemIndiceInvertido;

public interface Dicionario {
	public void inserir(ItemIndiceInvertido novoItem);
	public ItemIndiceInvertido remover(String chave);
	public ItemIndiceInvertido buscar(String chave);
}
