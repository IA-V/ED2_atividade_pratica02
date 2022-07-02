package tads;

import java.util.ArrayList;

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
	
	public void listar() {
		for(NoHash no: this.listaNos) {
			//System.out.println(no);
			if(no != null) {
				NoHash noAtual = no;
				NoHash noAnterior = noAtual;
				
				System.out.println("Indice "+no.getIndice());
				while(noAtual != null) {
					ArrayList<ParQtdId> pares = no.getItem().getParQtdId();
					System.out.println("Palavra = "+no.getItem().getPalavra());
					System.out.print("Pares: ");
					for(ParQtdId par: pares) {
						//System.out.println(count);
						System.out.print(par.getQtd()+" "+par.getIdProduto()+" | ");
					}
					System.out.println();
					noAnterior = noAtual;
					noAtual = noAtual.getProximoItem();
				}
			}
			System.out.println();
		}
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
		
		System.out.println(chave);
		System.out.println(hash+"\n");
		return hash;
	}
	
	public boolean isEmpty() {
		return this.listaNos.size()== 0;
	}
	
	public void inserir(ItemIndiceInvertido novoItem) {		
		// encontra o indice do novo no da lista hash
		NoHash novoNo = new NoHash(novoItem);
        int indiceNovoNo = this.calcularHash(novoNo.getChave());
        int idNovoItem = novoItem.getParQtdId().get(0).getIdProduto();
        
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
        			boolean encontrou = false;
        			if(novoNo.getChave().compareTo(noAtual.getChave()) == 0) {
        				adicionarNo = false;
        				//System.out.println("!!Aqui");
        				for(ParQtdId par: noAtual.getItem().getParQtdId()) {
        					/*for(ParQtdId par2: novoItem.getParQtdId()) {
        						System.out.println("par1 = "+par1.getIdProduto());
        						System.out.println("par2 = "+par2.getIdProduto());
        						
        					}*/
        					/*System.out.println("NoAtual item id = "+par.getIdProduto()+"\nNoAtual item nome = "+noAtual.getChave());
    						System.out.println("Novo item id = "+idNovoItem+"\nNovo item nome = "+novoNo.getChave());
    						System.out.println();*/
        					if(par.getIdProduto() == idNovoItem) {
                				//System.out.println("!!!Aqui");
                				noAtual.getItem().incrementarQtd(1, idNovoItem);
                				encontrou = true;
                				break;
                			}
                		}
        				
        				if(!encontrou) {
            				noAtual.getItem().addParQtdId(new ParQtdId(idNovoItem));
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
        
        // Se o fator de carga atingir ou passar de 0.7, o tamanho maximo da lista eh dobrado
        /*if((1.0 * this.tamanhoAtual) / this.tamanhoMax >= 0.7) {
        	 ArrayList<NoHash> temp = this.listaNos;
        	 this.tamanhoMax *= 2;
        	 this.tamanhoAtual = 0;
        	 this.listaNos = new ArrayList<>();
             
             
             this.inicializarArrayList();
  
             for (NoHash no : temp) {
                 while(no != null) {
                     this.inserir(no.getItem());
                 }
             }
        }*/
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
}
