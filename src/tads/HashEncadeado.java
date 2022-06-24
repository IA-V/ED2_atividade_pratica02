package tads;

import java.util.ArrayList;

import indice.ItemIndiceInvertido;
import interfaces.Dicionario;

public class HashEncadeado /*implements Dicionario*/ {
	private ArrayList<NoHash> listaNos;
	private int tamanhoMax;
	private int tamanhoAtual;
	
	public HashEncadeado (int tamanhoMax) {
		this.listaNos = new ArrayList<>();
		this.tamanhoMax = tamanhoMax;
		this.tamanhoAtual = 0;
	}
	
	private int calcularHash(String chave) {
		byte[] arrayBytes = chave.getBytes();
		
		int contador = 0;
		if(arrayBytes.length >= 8) {
			contador = 8;
		} else {
			contador = arrayBytes.length;
		}
		
		int soma = 0;
		for(int i = 0; i < contador; i++) {
			System.out.println(arrayBytes[i]);
			soma += arrayBytes[i];
		}
		
		int hash = (int)Math.floor(this.tamanhoMax*((soma*0.6180339887)%1));
		
		return hash;
	}
	
	public boolean isEmpty() {
		return this.listaNos.size()== 0;
	}
	
	public void inserir(NoHash novoNo) {		
		// encontra o indice do novo no da lista hash
        int indiceNovoNo = this.calcularHash(novoNo.getChave());
        
        novoNo.setIndice(indiceNovoNo);
        NoHash noAtual = null;
        
        if(this.isEmpty()) {
        	this.listaNos.add(indiceNovoNo, novoNo);
        } else {
        	if(this.listaNos.get(indiceNovoNo) != null) {
        		noAtual = this.listaNos.get(indiceNovoNo);
        		while(noAtual.getProximoItem() != null) {
        			noAtual = noAtual.getProximoItem();
        		}
        		
        		noAtual.setProximoItem(novoNo);
        	} else {
        		this.listaNos.add(indiceNovoNo, novoNo);
        	}
        }
        
        // Se o fator de carga atingir ou passar de 0.7, o tamanho maximo da lista eh dobrado
        if((1.0 * this.tamanhoAtual) / this.tamanhoMax >= 0.7) {
        	 ArrayList<NoHash> temp = this.listaNos;
        	 this.tamanhoMax *= 2;
        	 this.tamanhoAtual = 0;
        	 this.listaNos = new ArrayList<>();
             
             
             for (int i = 0; i < this.tamanhoMax; i++)
                 this.listaNos.add(null);
  
             for (NoHash no : temp) {
                 while(no != null) {
                     this.inserir(no);
                 }
             }
        }
        this.tamanhoAtual++;
	}
	
	public ItemIndiceInvertido remover(String chave) {
		if(this.isEmpty()) {
			return null;
		} else {
			NoHash noAtual = null;
			int indiceNoRemovido = this.calcularHash(chave);
			
			if(this.listaNos.get(indiceNoRemovido) != null) {
				NoHash noAnterior = null;
				noAtual = this.listaNos.get(indiceNoRemovido);
        		while(noAtual.getProximoItem() != null && noAtual.getChave().compareTo(chave) != 0) {
        			noAnterior = noAtual;
        			noAtual = noAtual.getProximoItem();
        		}
        		
        		if(noAtual.getChave().compareTo(chave) == 0) {
        			ItemIndiceInvertido dado = noAtual.getItem();
        			noAnterior.setProximoItem(noAtual.getProximoItem());
        			noAtual = null;
        			return dado;
        		} else if(noAtual.getProximoItem() == null) {
        			return null;
        		}
			}
		}
		return null;
	}
	
	public ItemIndiceInvertido buscar(String chave) {
		int indiceNoBuscado = this.calcularHash(chave);
		
		return this.listaNos.get(indiceNoBuscado).getItem();
	}
}
