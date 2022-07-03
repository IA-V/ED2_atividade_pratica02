package tads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import indice.ItemIndiceInvertido;
import indice.ParQtdId;
import interfaces.Dicionario;

public class HashEncadeado implements Dicionario {
	private ArrayList<NoHash> listaNos;
	private int tamanhoMax;
	private int tamanhoAtual;
	
	public HashEncadeado (int tamanhoMax) {
		this.listaNos = new ArrayList<>();
		this.tamanhoMax = tamanhoMax;
		this.tamanhoAtual = 0;
		this.inicializarArrayList();
	}
	
	public int getTamanhoAtual(){
		return this.tamanhoAtual;
	}
	private void inicializarArrayList() {
		for(int i = 0; i < this.tamanhoMax; i++) {
			this.listaNos.add(null);
		}
	}
	
	public HashMap listar() {
		HashMap<Integer, ItemIndiceInvertido> hashMap = new HashMap<>();
		for(NoHash no: this.listaNos) {
			//System.out.println(no);
			if(no != null) {
				NoHash noAtual = no;
				NoHash noAnterior = noAtual;
				int cont = 0;
				Double peso = 0.0;
				Double relevancia = 0.0;


				
				while(noAtual != null) {
					HashMap<Integer, Integer> pares = no.getItem().getParQtdId();
					// System.out.println("Palavra = "+no.getItem().getPalavra());
					// System.out.print("Pares: ");

					for(Integer par: pares.keySet()) {
						// System.out.print(par.getQtd()+" "+par.getIdProduto());
						cont ++;
						ItemIndiceInvertido indiceInvertido = new ItemIndiceInvertido(no.getItem().getPalavra());

						peso = calculaPeso(no.getIndice(), pares.get(par), par);
						relevancia = calculaRelevancia(no.getItem().getPalavra(), peso, no.getIndice());
						indiceInvertido.setRelevancia(relevancia);
						// System.out.println("[" + par.getIdProduto()+"]" + " --->" + indiceInvertido.toString());
						hashMap.put(par, indiceInvertido);
					}
					
					noAnterior = noAtual;
					noAtual = noAtual.getProximoItem();
				}
			}
		}
		return hashMap;
	}
	
	public int calcularHash(String chave) {
		byte[] arrayBytes = chave.getBytes();
		
		int contador = 0;
		if(arrayBytes.length >= 8) {
			contador = 8;
		} else {
			contador = arrayBytes.length;
		}
		
		int soma = 0;
		for(int i = 0; i < contador; i++) {
			soma += arrayBytes[i];
		}
		
		int hash = (int)Math.floor(this.tamanhoMax*((soma*0.6180339887)%1));
		
		// System.out.println(chave);
		// System.out.println(hash+"\n");
		return hash;
	}
	
	public boolean isEmpty() {
		return this.listaNos.size()== 0;
	}
	
	public void inserir(ItemIndiceInvertido novoItem) {		
		// encontra o indice do novo no da lista hash
		NoHash novoNo = new NoHash(novoItem);
        int indiceNovoNo = this.calcularHash(novoNo.getChave());
        Set<Integer> idNovoItem = novoItem.getParQtdId().keySet();
        novoNo.setIndice(indiceNovoNo);
        NoHash noAtual = null;
        NoHash noAnterior = null;
        
        if(this.isEmpty()) {
        	this.listaNos.set(indiceNovoNo, novoNo);
        } else {
        	if((noAtual = this.listaNos.get(indiceNovoNo)) != null) {
        		boolean adicionarNo = true;
        		/*System.out.println(noAtual);
        		System.out.println(noAtual.getProximoItem());*/
        		while(noAtual != null) {
        			Integer id = idNovoItem.iterator().next();
        			Integer qtd = noAtual.getItem().getParQtdId().get(id);
					
					//System.out.println("qtd = "+qtd+" id = "+id+"\n");
        			boolean encontrou = false;
        			if(novoNo.getChave().compareTo(noAtual.getChave()) == 0) {
        				adicionarNo = false;
        				if(noAtual.getItem().getParQtdId().containsKey(id) && qtd != null) {
        					//System.out.println("qtd = "+qtd+" id = "+id+"\n");
        					noAtual.getItem().addParQtdId(id, qtd+1);
            				encontrou = true;
        				}
        				
        				if(!encontrou) {
        					noAtual.getItem().addParQtdId(id, 1);
            			}
        			}
        			
        			
        			noAnterior = noAtual;
        			noAtual = noAtual.getProximoItem();
        		}
        		if(adicionarNo) {
        			noAnterior.setProximoItem(novoNo);
        		}
        	} else {
        		this.listaNos.set(indiceNovoNo, novoNo);
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
	
	/*public ItemIndiceInvertido buscar(String chave) {
		if(this.isEmpty()) {
			return null;
		}
		
		int indiceNoBuscado = this.calcularHash(chave);
		
		NoHash noAtual = this.listaNos.get(indiceNoBuscado);
		
		if(noAtual != null) {
			while(noAtual.getProximoItem() != null && noAtual.getChave().compareTo(chave) != 0) {
				noAtual = noAtual.getProximoItem();
			}
			
			if(noAtual.getChave().compareTo(chave) == 0) {
				return noAtual.getItem();
			} else if(noAtual.getProximoItem() == null) {
				return null;
			}
		}
		
		return null;
	}*/
	
	public ItemIndiceInvertido buscar(String chave) {
        if(this.isEmpty()) {
            return null;
        }
        
        Double peso = 0.0;
        Double relevancia = 0.0;

        int indiceNoBuscado = this.calcularHash(chave);
        
        NoHash noAtual = this.listaNos.get(indiceNoBuscado);
        
        if(noAtual != null) {
            while(noAtual.getProximoItem() != null && noAtual.getChave().compareTo(chave) != 0) {
                noAtual = noAtual.getProximoItem();
            }
            
            if(noAtual.getChave().compareTo(chave) == 0) {
                //System.out.println(noAtual.getItem().toString());
                ItemIndiceInvertido indiceInvertido = new ItemIndiceInvertido(noAtual.getItem().getPalavra());

                peso = calculaPeso(noAtual.getIndice(), this.calcularHash(chave) , noAtual.getIndice());
                relevancia = calculaRelevancia(noAtual.getItem().getPalavra(), peso, noAtual.getIndice());
                indiceInvertido.setRelevancia(relevancia);
                //System.out.println("[" +  this.calcularHash(chave) +"]" + " --->" + indiceInvertido.toString());
                // hashMap.put(par.getIdProduto(), indiceInvertido);
            
                return noAtual.getItem();
            } else if(noAtual.getProximoItem() == null) {
                return null;
            }
        }
        
        return null;
    }
	
	public ItemIndiceInvertido buscarPeloId(String chave) {
		if(this.isEmpty()) {
			return null;
		}
		
		int indiceNoBuscado = this.calcularHash(chave);
		
		NoHash noAtual = this.listaNos.get(indiceNoBuscado);
		
		if(noAtual != null) {
			while(noAtual.getProximoItem() != null && noAtual.getChave().compareTo(chave) != 0) {
				noAtual = noAtual.getProximoItem();
			}
			
			if(noAtual.getChave().compareTo(chave) == 0) {
				return noAtual.getItem();
			} else if(noAtual.getProximoItem() == null) {
				return null;
			}
		}
		
		return null;
	}

	public static void criarRecomendacao(HashEncadeado hash, String termo){
		int count = 0;
		Double peso = 0.0;

		for (int i=0; i<hash.getTamanhoAtual(); i++){
			ItemIndiceInvertido item = hash.buscar(termo);
			if(item != null) count ++;
			
			peso = calculaPeso(hash.getTamanhoAtual(), count, 1000);
			calculaRelevancia(termo, peso, 2); //Onde numTermosDistintos é o número de termos distintos da descrição i
		}
		System.out.println();
	}

	public static Double calculaRelevancia(String termo, Double peso, Integer numTermosDistintos){ 
		Double relevancia;
		relevancia = 1/ (numTermosDistintos * peso);
		// System.out.println(relevancia + " relevancia");
		return relevancia;
	}

	public static Double calculaPeso(Integer numProdutosDS, Integer numOcorrencias, Integer qtdProdutosComTermo){
		Double peso = 0.0;

		peso = numOcorrencias * Math.log(numProdutosDS)/qtdProdutosComTermo;
		return peso;
	}

	public HashMap buscarTermo(String termo) {
		HashMap<Integer, Integer> hashMap = new HashMap();
		for(NoHash no: this.listaNos) {
			if(no != null) {
				NoHash noAtual = no;
				NoHash noAnterior = noAtual;
				int cont = 0;
				Double peso = 0.0;
				Double relevancia = 0.0;



				while(noAtual != null) {
					ArrayList<ParQtdId> pares = no.getItem().getParQtdId();
					// System.out.println("Palavra = "+no.getItem().getPalavra());
					System.out.print("Pares: ");
					for(ParQtdId par: pares) {
						hashMap.put(par.getQtd(), par.getIdProduto());

						// System.out.print(par.getQtd()+" "+par.getIdProduto());
						cont ++;
						
						peso = calculaPeso(no.getIndice(), par.getQtd(), par.getIdProduto());
						relevancia = calculaRelevancia(no.getItem().getPalavra(), peso, no.getIndice());
						System.out.println(par.toString());
					}
					
					noAnterior = noAtual;
					noAtual = noAtual.getProximoItem();
				}
			}
		}
		return hashMap;
	}
	
}
