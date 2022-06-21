package interfaces;

import indice.IndiceInvertido;

public interface Dicionario {
	public void inserir(IndiceInvertido item);	
	public IndiceInvertido remover(IndiceInvertido item);	
	public IndiceInvertido buscar(IndiceInvertido item);
}
