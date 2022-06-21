package tads;
import indice.*;
import interfaces.*;

public class HashingEncadeado <K> implements Dicionario {
	private IndiceInvertido item;
	
	private K chave; // Deve-se usar a palavra do indice para calcular o valor da chave
	private IndiceInvertido proximaPos; // proxima posicao da lista
	private IndiceInvertido proximoItem; // proximo item da posicao atual
	
	public HashingEncadeado(IndiceInvertido item, IndiceInvertido proximaPos, IndiceInvertido proximoItem, K chave) {
		this.item = item;
		this.proximaPos = proximaPos;
		this.proximoItem = proximoItem;
		this.chave = chave;
	}
	
	public void inserir(IndiceInvertido item) {
		
	}
	
	public IndiceInvertido remover(IndiceInvertido item) {
		
	}
	
	public IndiceInvertido buscar(IndiceInvertido item) {
		
	}
}
