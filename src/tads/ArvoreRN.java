package tads;

import java.util.ArrayList;

import indice.ItemIndiceInvertido;
import indice.ParQtdId;
import interfaces.Dicionario;

public class ArvoreRN implements Dicionario {
	private NoArvoreRN raiz;
	
	public ArvoreRN() {
        this.raiz = null;
    }

    public boolean isEmpty() {
        return this.raiz == null;
    }
    
    public void listar() {
    	this.listar(this.raiz);
    }
    
    private void listar(NoArvoreRN raiz) {
    	if(raiz != null) {
    		this.listar(raiz.getEsquerda());
    		
    		ArrayList<ParQtdId> pares = raiz.getElemento().getParQtdId();
			System.out.println("Palavra = "+raiz.getElemento().getPalavra());
			System.out.print("Pares: ");
			for(ParQtdId par: pares) {
				System.out.print(par.getQtd()+" "+par.getIdProduto()+" | .");
			}
			System.out.println();
        	System.out.println();
        	
        	this.listar(raiz.getDireita());
    	}
    	
    	
    }
    
    public ItemIndiceInvertido buscar(String chave) {
    	ItemIndiceInvertido item = new ItemIndiceInvertido(chave);
		return this.buscar(item, this.raiz);
	}
    
    private ItemIndiceInvertido buscar(ItemIndiceInvertido item, NoArvoreRN raiz) {    	
    	if(raiz != null) {
    		ItemIndiceInvertido valorRaiz = raiz.getElemento();
    		System.out.println(this.raiz.getElemento().getPalavra());
    		System.out.println(valorRaiz.getPalavra());
        	if (item.getPalavra().compareTo(valorRaiz.getPalavra()) < 0) {
                return this.buscar(item, raiz.getEsquerda());
            } else if (item.getPalavra().compareTo(valorRaiz.getPalavra()) > 0) {
            	return this.buscar(item, raiz.getDireita());
            } else {
                return raiz.getElemento();
            }
    	} else {
    		return null;
    	}
    	
        //this.buscar(item, raiz);
    	
        /*if(raiz != null) {
            ItemIndiceInvertido valorRaiz = raiz.getElemento();

            if (item.getPalavra().compareTo(valorRaiz.getPalavra()) < 0) {
                raiz = raiz.getEsquerda();
            } else if (item.getPalavra().compareTo(valorRaiz.getPalavra()) > 0) {
                raiz = raiz.getDireita();
            } else {
                resultado = raiz.getElemento();
            }
            this.buscar(item, raiz);
        }*/

        //return resultado;
	}
    
    public void inserir(ItemIndiceInvertido item) {
    	NoArvoreRN novoNo = new NoArvoreRN(item);
    	
    	NoArvoreRN noAux = null;
    	NoArvoreRN raizAux = this.raiz;
    	
    	//System.out.println(this.raiz);
    	
    	while(raizAux != null) {
    		noAux = raizAux;
    		if(novoNo.getChave().compareTo(raizAux.getChave()) < 0) {
    			raizAux = raizAux.getEsquerda();
    		} else if(novoNo.getChave().compareTo(raizAux.getChave()) > 0) {
    			raizAux = raizAux.getDireita();
    		} else {
    			int idNovoItem = novoNo.getElemento().getParQtdId().get(0).getIdProduto();
    			boolean encontrou = false;
    			for(ParQtdId par: raizAux.getElemento().getParQtdId()) {
    				if(par.getIdProduto() == idNovoItem) {
            			raizAux.getElemento().incrementarQtd(1, idNovoItem);
            			encontrou = true;
            			break;
            		}
            	}
    			
    			if(!encontrou) {
    				raizAux.getElemento().addParQtdId(new ParQtdId(idNovoItem));
    			}
    		}
    	}
    	
    	//System.out.println(noAux);
    	novoNo.setPai(noAux);
    	//System.out.println(novoNo.getPai());
    	
    	if(noAux == null) {
    		this.raiz = novoNo;
    	} else if(novoNo.getChave().compareTo(noAux.getChave()) < 0) {
    		noAux.setEsquerda(novoNo);
    	} else {
    		noAux.setDireita(novoNo);
    	}
    	
    	novoNo.setEsquerda(null);
    	novoNo.setDireita(null);
    	this.insertFixup(novoNo);
    	/*System.out.println("Pai da Raiz = "+this.raiz.getPai());
    	System.out.println("Raiz = "+this.raiz.getChave());
    	System.out.println("Cor da Raiz = "+this.raiz.getCor());
    	System.out.println("filho esq da raiz = "+this.raiz.getEsquerda());
    	System.out.println("filho dir da raiz = "+this.raiz.getDireita());
    	System.out.println();*/
    }
    
    private void insertFixup(NoArvoreRN novoNo) {
    	NoArvoreRN novoNoAux = novoNo;
    	//System.out.println("Novo no = " + novoNo + ", pai do novo no = " + novoNo.getPai());
    	if(novoNo == this.raiz) {
    		novoNo.setCor(false);
    	} else {
    		while(novoNo.getPai().getCor()) {
        		if(novoNo.getPai() == novoNo.getPai().getPai().getEsquerda()) {
        			NoArvoreRN noAux = novoNo.getPai().getPai().getDireita();
        			if(noAux != null && noAux.getCor()) {
        				novoNo.getPai().setCor(false);
        				noAux.setCor(false);
        				novoNo.getPai().getPai().setCor(true);
        				novoNo = novoNo.getPai().getPai();
        			} else if(novoNo == novoNo.getPai().getDireita()) {
        				novoNo = novoNo.getPai();
        				this.rotacaoEsquerda(novoNo);
        			}
        			
        			/*novoNo.getPai().setCor(false);
        			novoNo.getPai().getPai().setCor(true);
        			
        			this.rotacaoDireita(novoNo.getPai().getPai());*/
        			
        			if(novoNo.getPai() != null) {
        				novoNo.getPai().setCor(false);
        				
        				if(novoNo.getPai().getPai() != null) {
            				novoNo.getPai().getPai().setCor(true);
            			}
        				
        				this.rotacaoDireita(novoNo.getPai().getPai());
        			} else {
        				novoNo = novoNoAux;
        			}
        		} else {
        			NoArvoreRN noAux = novoNo.getPai().getPai().getEsquerda();
        			if(noAux != null && noAux.getCor()) {
        				novoNo.getPai().setCor(false);
        				noAux.setCor(false);
        				novoNo.getPai().getPai().setCor(true);
        				novoNo = novoNo.getPai().getPai();
        			} else if(novoNo == novoNo.getPai().getEsquerda()) {
        				novoNo = novoNo.getPai();
        				this.rotacaoDireita(novoNo);
        			}
        			
        			/*novoNo.getPai().setCor(false);
        			novoNo.getPai().getPai().setCor(true);
        			
        			this.rotacaoEsquerda(novoNo.getPai().getPai());*/
        			
        			if(novoNo.getPai() != null) {
        				novoNo.getPai().setCor(false);
        				
        				if(novoNo.getPai().getPai() != null) {
            				novoNo.getPai().getPai().setCor(true);
            			}
        				
        				this.rotacaoEsquerda(novoNo.getPai().getPai());
        			} else {
        				novoNo = novoNoAux;
        			}
        		}
        	}
        	
        	this.raiz.setCor(false);
    	}
    }
    
    public ItemIndiceInvertido remover(String chave) {
    	return this.remover(chave, this.raiz);
    }
    
    private ItemIndiceInvertido remover(String chave, NoArvoreRN raiz) {
    	NoArvoreRN noAux1 = null;
    	NoArvoreRN noAux2 = null;
    	ItemIndiceInvertido itemRemovido = null;
    	
    	if(raiz != null) {
    		if(chave.compareTo(raiz.getChave()) < 0) {
    			this.remover(chave, raiz.getEsquerda());
    		} else if (chave.compareTo(raiz.getChave()) > 0) {
    			this.remover(chave, raiz.getDireita());
    		} else if (chave.compareTo(raiz.getChave()) == 0) {
    			
    			itemRemovido = raiz.getElemento();
    			
    			if(raiz.getEsquerda() == null || raiz.getDireita() == null) {
    				noAux1 = raiz;
    	    	} else {
    	    		noAux1 = this.sucessorDireita(raiz.getDireita());
    	    		
    	    	}
    			
    			//System.out.println(noAux1.getChave());
    	    	
    	    	if(noAux1.getEsquerda() != null) {
    	    		noAux2 = noAux1.getEsquerda();
    	    		noAux2.setPai(noAux1.getPai());
    	    	} else if(noAux1.getDireita() != null) {
    	    		noAux2 = noAux1.getDireita();
    	    		noAux2.setPai(noAux1.getPai());
    	    	}
    	    	System.out.println(noAux2);
    			if(noAux2 != null) {
    				if(noAux1.getPai() == null) {
        	    		this.raiz = noAux2;
        	    	} else if(noAux1 == noAux1.getPai().getEsquerda()) {
        	    		noAux1.getPai().setEsquerda(noAux2);
        	    	} else {
        	    		noAux1.getPai().setDireita(noAux2);
        	    	}
    			}
    	    	
    	    	if(noAux1 != raiz) {
    	    		System.out.println("!!!Passou aq");
    	    		raiz.setElemento(noAux1.getElemento());
    	    		NoArvoreRN noAux3 = noAux1;
    	    		while(noAux3.getDireita() != null) {
    	    			noAux3.setElemento(noAux3.getDireita().getElemento());
    	    			noAux3 = noAux3.getDireita();
    	    		}
    	    		if(this.verificarCor(noAux1)) {
    	    			if(noAux1 == noAux1.getPai().getDireita()) {
        	    			noAux1.getPai().setDireita(null);
        	    		} else {
        	    			noAux1.getPai().setEsquerda(null);
        	    		}
        	    		
        	    		noAux1.setPai(null);
    	    		}
    	    	}
    	    	
    	    	if(!this.verificarCor(noAux1)) {
    	    		if(noAux2 != null) {    	    			
    	    			System.out.println("!!Passou aq");
    	    			this.deleteFixup(noAux2);
    	    		} else {
    	    			this.deleteFixup(raiz);
    	    			System.out.println("!Passou aq");
    	    			if(noAux1.getPai() == null) {
            	    		this.raiz = noAux2;
            	    	} else if(noAux1 == noAux1.getPai().getEsquerda()) {
            	    		noAux1.getPai().setEsquerda(noAux2);
            	    	} else {
            	    		noAux1.getPai().setDireita(noAux2);
            	    	}
    	    		}
    	    	}
    		}
    	}
    	
    	return itemRemovido;
    }
    
    private boolean verificarCor(NoArvoreRN no) {
    	if(no != null) {
    		return no.getCor();
    	}
    	return false;
    }
    
    private void alterarCor(NoArvoreRN no, boolean cor) {
    	if(no != null) {
    		no.setCor(cor);
    	}
    }
    
    private void deleteFixup(NoArvoreRN no) {
		while(no != this.raiz && !no.getCor()) {
			if(no == no.getPai().getEsquerda()) {
				
				NoArvoreRN noAux = no.getPai().getDireita();
				if(noAux != null && this.verificarCor(noAux)) {
					this.alterarCor(noAux, false);
					this.alterarCor(no.getPai(), true);
					this.rotacaoEsquerda(no.getPai());
					noAux = no.getPai().getDireita();
				}
				
				if(noAux != null && !this.verificarCor(noAux.getEsquerda()) && !this.verificarCor(noAux.getDireita())) {
					this.alterarCor(noAux, true);
					no = no.getPai();
				} else if(noAux != null && !this.verificarCor(noAux.getDireita())) {
					this.alterarCor(noAux.getEsquerda(), false);
					this.alterarCor(noAux, true);
					
					this.rotacaoDireita(noAux);
					
					noAux = no.getPai().getDireita();
					
					this.alterarCor(noAux, this.verificarCor(no.getPai()));
					this.alterarCor(no.getPai(), false);

					this.alterarCor(noAux.getDireita(), false);

					this.rotacaoEsquerda(no.getPai());
					
					no = this.raiz;
				} else {
					this.rotacaoEsquerda(no.getPai());
					no = this.raiz;
				}
			} else {
				NoArvoreRN noAux = no.getPai().getEsquerda();
				if(noAux != null && this.verificarCor(noAux)) {
					noAux.setCor(false);
					no.getPai().setCor(true);
					this.rotacaoDireita(no.getPai());
					noAux = no.getPai().getEsquerda();
				}
				
				if(noAux != null && !this.verificarCor(noAux.getDireita()) && !this.verificarCor(noAux.getEsquerda())) {
					noAux.setCor(true);
					no = no.getPai();
				} else if(noAux != null && !this.verificarCor(noAux.getEsquerda())) {
					this.alterarCor(noAux.getDireita(), false);
					this.alterarCor(noAux, true);
					
					this.rotacaoEsquerda(noAux);
					
					noAux = no.getPai().getEsquerda();
					
					this.alterarCor(noAux, this.verificarCor(no.getPai()));
					this.alterarCor(no.getPai(), false);

					this.alterarCor(noAux.getEsquerda(), false);

					this.rotacaoDireita(no.getPai());
					
					no = this.raiz;
					
				} else {
					this.rotacaoDireita(no.getPai());
					no = this.raiz;
				}
			}
		}
		this.alterarCor(no, false);
	}

	private NoArvoreRN sucessorDireita(NoArvoreRN no) {
	        if(no.getEsquerda() == null) {
	        	/**/
		        return no;
	        } else {
	        	return sucessorDireita(no.getEsquerda());
	        }	        
	}
	
	private NoArvoreRN sucessorEsquerda(NoArvoreRN no) {
        if(no.getDireita() == null) {
        	/**/
	        return no;
        } else {
        	return sucessorEsquerda(no.getDireita());
        }	        
}
    
    private void rotacaoEsquerda(NoArvoreRN k1) {
		NoArvoreRN k2 = k1.getDireita();
		
		if(k1.getDireita() != null) {
			k1.setDireita(k2.getEsquerda());
		} else {
			k1.setDireita(null);
		}
		
		if(k2 != null) {
			if(k2.getEsquerda() != null) {
				k2.getEsquerda().setPai(k1);
			}
			
			k2.setPai(k1.getPai());
			
			if(k1.getPai() == null) {
				this.raiz = k2;
			} else if(k1 == k1.getPai().getEsquerda()) {
				k1.getPai().setEsquerda(k2);
			} else {
				k1.getPai().setDireita(k2);
			}
			
			k2.setEsquerda(k1);
			k1.setPai(k2);
		}
		
		
		
		
		//return k2;
	}
    
    private void rotacaoDireita(NoArvoreRN k2) {
		NoArvoreRN k1 = k2.getEsquerda();
		
		if(k2.getEsquerda() != null) {
			k2.setEsquerda(k1.getDireita());
		} else {
			k2.setEsquerda(null);
		}
		
		if(k1 != null) {
			if(k1.getDireita() != null) {
				k1.getDireita().setPai(k2);
			}
			
			k1.setPai(k2.getPai());
			
			if(k2.getPai() == null) {
				this.raiz = k1;
			} else if(k2 == k2.getPai().getDireita()) {
				k2.getPai().setDireita(k1);
			} else {
				k2.getPai().setEsquerda(k1);
			}
			
			k1.setDireita(k2);
			k2.setPai(k1);
		}
		
		//return k1;
	}
}
