package tads;

import indice.ItemIndiceInvertido;
import interfaces.Dicionario;

public class ArvoreRN implements Dicionario {
	private NoArvoreRN raiz;
	
	public ArvoreRN() {
        this.raiz = null;
    }

    public boolean isEmpty() {
        return this.raiz == null;
    }
    
    public ItemIndiceInvertido buscar(String chave) {
    	ItemIndiceInvertido item = this.buscar(chave);
		return this.buscar(item, this.raiz);
	}
    
    private ItemIndiceInvertido buscar(ItemIndiceInvertido item, NoArvoreRN raiz) {
    	ItemIndiceInvertido resultado = null;

        if(raiz != null) {
            ItemIndiceInvertido valorRaiz = raiz.getElemento();

            if (item.getPalavra().compareTo(valorRaiz.getPalavra()) < 0) {
                raiz = raiz.getEsquerda();
            } else if (item.getPalavra().compareTo(valorRaiz.getPalavra()) > 0) {
                raiz = raiz.getDireita();
            } else {
                resultado = raiz.getElemento();
            }
            resultado = this.buscar(item, raiz);

        }

        return resultado;
	}
    
    public void inserir(ItemIndiceInvertido item) {
    	NoArvoreRN novoNo = new NoArvoreRN(item);
    	
    	NoArvoreRN noAux = null;
    	NoArvoreRN raizAux = this.raiz;
    	
    	while(raizAux != null) {
    		noAux = raizAux;
    		if(novoNo.getChave().compareTo(raizAux.getChave()) < 0) {
    			raizAux = raizAux.getEsquerda();
    		} else {
    			raizAux = raizAux.getDireita();
    		}	
    	}
    	
    	novoNo.setPai(noAux);
    	
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
    }
    
    private void insertFixup(NoArvoreRN novoNo) {
    	while(novoNo.getPai().getCor()) {
    		if(novoNo.getPai() == novoNo.getPai().getPai().getEsquerda()) {
    			NoArvoreRN noAux = novoNo.getPai().getPai().getDireita();
    			if(noAux.getCor()) {
    				novoNo.getPai().setCor(false);
    				noAux.setCor(false);
    				novoNo.getPai().getPai().setCor(true);
    				novoNo = novoNo.getPai().getPai();
    			} else if(novoNo == novoNo.getPai().getDireita()) {
    				novoNo = novoNo.getPai();
    				this.rotacaoEsquerda(novoNo);
    			}
    			
    			novoNo.getPai().setCor(false);
    			novoNo.getPai().getPai().setCor(true);
    			
    			this.rotacaoDireita(novoNo.getPai().getPai());
    		} else {
    			NoArvoreRN noAux = novoNo.getPai().getPai().getEsquerda();
    			if(noAux.getCor()) {
    				novoNo.getPai().setCor(false);
    				noAux.setCor(false);
    				novoNo.getPai().getPai().setCor(true);
    				novoNo = novoNo.getPai().getPai();
    			} else if(novoNo == novoNo.getPai().getEsquerda()) {
    				novoNo = novoNo.getPai();
    				this.rotacaoDireita(novoNo);
    			}
    			
    			novoNo.getPai().setCor(false);
    			novoNo.getPai().getPai().setCor(true);
    			
    			this.rotacaoEsquerda(novoNo.getPai().getPai());
    		}
    	}
    	
    	this.raiz.setCor(false);
    }
    
    public ItemIndiceInvertido remover(String chave) {
    	return this.remover(chave, this.raiz);
    }
    
    private ItemIndiceInvertido remover(String chave, NoArvoreRN raiz) {
    	NoArvoreRN noAux1 = null;
    	NoArvoreRN noAux2 = null;
    	
    	if(chave.compareTo(raiz.getChave()) > 0) {
			this.remover(chave, raiz.getEsquerda());
		} else if (chave.compareTo(raiz.getChave()) < 0) {
			this.remover(chave, raiz.getDireita());
		} else if (chave.compareTo(raiz.getChave()) == 0) {
		
			if(raiz.getEsquerda() == null || raiz.getDireita() == null) {
	    		noAux1 = raiz;
	    	} else {
	    		noAux1 = this.sucessorEsquerda(raiz, raiz.getPai());
	    	}
	    	
	    	if(noAux1.getEsquerda() != null) {
	    		noAux2 = noAux1.getEsquerda();
	    	} else {
	    		noAux2 = noAux1.getDireita();
	    	}
	    	
	    	noAux2.setPai(noAux1.getPai());
	    	
	    	if(noAux1.getPai() == null) {
	    		this.raiz = noAux2;
	    	} else if(noAux1 == noAux1.getPai().getEsquerda()) {
	    		noAux1.getPai().setEsquerda(noAux2);
	    	} else {
	    		noAux1.getPai().setDireita(noAux2);
	    	}
	    	
	    	if(noAux1 != raiz) {
	    		raiz.setElemento(noAux1.getElemento());
	    	}
	    	
	    	if(!noAux1.getCor()) {
	    		this.deleteFixup(noAux2);
	    	}
		}
    	return noAux1.getElemento();
    }
    
    private void deleteFixup(NoArvoreRN no) {
		while(no != this.raiz && !no.getCor()) {
			if(no == no.getPai().getEsquerda()) {
				NoArvoreRN noAux = no.getPai().getDireita();
				if(noAux.getCor()) {
					noAux.setCor(false);
					no.getPai().setCor(true);
					this.rotacaoEsquerda(no.getPai());
					noAux = no.getPai().getDireita();
				}
				
				if(!noAux.getEsquerda().getCor() && !noAux.getDireita().getCor()) {
					noAux.setCor(true);
					no = no.getPai();
				} else if(!noAux.getEsquerda().getCor()) {
					noAux.getEsquerda().setCor(false);
					noAux.setCor(true);
					this.rotacaoDireita(noAux);
					noAux = no.getPai().getDireita();
					noAux.setCor(no.getPai().getCor());
					no.getPai().setCor(false);
					noAux.getDireita().setCor(false);
					this.rotacaoEsquerda(no.getPai());
					no = this.raiz;
				}
			} else {
				NoArvoreRN noAux = no.getPai().getEsquerda();
				if(noAux.getCor()) {
					noAux.setCor(false);
					no.getPai().setCor(true);
					this.rotacaoDireita(no.getPai());
					noAux = no.getPai().getEsquerda();
				}
				
				if(!noAux.getDireita().getCor() && !noAux.getEsquerda().getCor()) {
					noAux.setCor(true);
					no = no.getPai();
				} else if(!noAux.getDireita().getCor()) {
					noAux.getDireita().setCor(false);
					noAux.setCor(true);
					this.rotacaoEsquerda(noAux);
					noAux = no.getPai().getEsquerda();
					noAux.setCor(no.getPai().getCor());
					no.getPai().setCor(false);
					noAux.getEsquerda().setCor(false);
					this.rotacaoDireita(no.getPai());
					no = this.raiz;
				}
			}
		}
		no.setCor(false);
	}

	public NoArvoreRN sucessorEsquerda(NoArvoreRN no, NoArvoreRN noPai) {
		if(no != null) {
	        if(no.getDireita() != null) {
	            sucessorEsquerda(no.getEsquerda(), no);
	        } else {
	            if(no.getEsquerda() != null) {
	            	noPai.setDireita(no.getEsquerda());
	            	NoArvoreRN noRemovido = no;
	                no = null;
	                return noRemovido;
	            } else {
	            	noPai.setDireita(null);
	            	NoArvoreRN noRemovido = no;
	                no = null;
	                return noRemovido;
	            }
	        }
	        //*data = NULL;
	        NoArvoreRN noRemovido = no;
	        return noRemovido;
	    } else {
	    	return null;
	    }
	}
    
    private void rotacaoEsquerda(NoArvoreRN k1) {
		NoArvoreRN k2 = k1.getDireita();
		k1.setDireita(k2.getEsquerda());
		k2.getEsquerda().setPai(k1);
		
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
		
		//return k2;
	}
    
    private void rotacaoDireita(NoArvoreRN k2) {
		NoArvoreRN k1 = k2.getEsquerda();
		k2.setEsquerda(k1.getDireita());
		k1.getDireita().setPai(k2);
		
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
		
		//return k1;
	}
}
