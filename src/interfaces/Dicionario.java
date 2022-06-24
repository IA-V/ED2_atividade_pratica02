package interfaces;

import indice.ItemIndiceInvertido;

public interface Dicionario {
	public void inserir(ItemIndiceInvertido item);	
	public ItemIndiceInvertido remover(ItemIndiceInvertido item);	
	public ItemIndiceInvertido buscar(ItemIndiceInvertido item);
}
