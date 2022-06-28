package tads;

import indice.ItemIndiceInvertido;
import interfaces.Dicionario;

public class ArvoreAvl implements Dicionario {
    private NoArvoreAvl raiz;
    
    public ArvoreAvl() {
        this.raiz = null;
    }

    public boolean isEmpty() {
        return this.raiz == null;
    }
    
    public void inserir(ItemIndiceInvertido item) {
    	this.raiz = this.inserir(item, this.raiz);
    }
    
    private NoArvoreAvl inserir(ItemIndiceInvertido item, NoArvoreAvl raiz) {
    	NoArvoreAvl raizEsq;
        NoArvoreAvl raizDir;
        int fatorBalanceamento;
    	
    	if(raiz == null) {
    		raiz = new NoArvoreAvl(item);
    	} else if (item.getPalavra().compareTo(raiz.getElemento().getPalavra()) < 0) {
            raiz.setEsquerda(this.inserir(item, raiz.getEsquerda()));
            
            raizEsq = raiz.getEsquerda();
            raizDir = raiz.getDireita();
            fatorBalanceamento = raizEsq.getAltura() - raizDir.getAltura(); 
            
            if(fatorBalanceamento  == 2) {
                if(item.getPalavra().compareTo(raizEsq.getElemento().getPalavra()) < 0) {
                    raiz = this.rotacaoEsquerda(raiz);
                } else {
                    raiz = this.rotacaoDuplaEsquerda(raiz);
                }
            }
        } else if(item.getPalavra().compareTo(raiz.getElemento().getPalavra()) > 0) {
        	raiz.setDireita(this.inserir(item, raiz.getDireita()));

        	raizEsq = raiz.getEsquerda();
            raizDir = raiz.getDireita();
            fatorBalanceamento = raizDir.getAltura() - raizEsq.getAltura();
            
            if(fatorBalanceamento == 2)
                if(item.getPalavra().compareTo(raizDir.getElemento().getPalavra()) < 0) {
                    raiz = this.rotacaoDireita(raiz);
                } else {
                    raiz = this.rotacaoDuplaDireita(raiz);
                }
        } else {

          ;  // Duplicate; do nothing
        }
    	
        raiz.setAltura(Math.max(raiz.getEsquerda().getAltura(), raiz.getDireita().getAltura()) + 1);

        return raiz;
	}
	
    public ItemIndiceInvertido remover(String palavra) {
    	ItemIndiceInvertido item = this.buscar(palavra);
    	return this.remover(item, this.raiz, null);
    }
    
	private ItemIndiceInvertido remover(ItemIndiceInvertido item, NoArvoreAvl raiz, NoArvoreAvl noPai) {
		if(raiz != null) {
			ItemIndiceInvertido elementoAtual = raiz.getElemento();
			if(elementoAtual.getPalavra().compareTo(item.getPalavra()) > 0) {
				this.remover(item, raiz.getEsquerda(), raiz);
			} else if (elementoAtual.getPalavra().compareTo(item.getPalavra()) < 0) {
				this.remover(item, raiz.getDireita(), raiz);
			} else if (elementoAtual.getPalavra().compareTo(item.getPalavra()) == 0) {
				
				if(raiz.getEsquerda() == null && raiz.getDireita() == null) {
	                NoArvoreAvl noRemovido = raiz;
	                raiz = null;
	                return noRemovido.getElemento();
	            } else if(raiz.getEsquerda() == null) {
	            	noPai.setDireita(raiz);
	                NoArvoreAvl noRemovido = raiz;
	                raiz = null;
	                return noRemovido.getElemento();
	            } else if(raiz.getDireita() == null) {
	            	noPai.setEsquerda(raiz);
	            	NoArvoreAvl noRemovido = raiz;
	                raiz = null;
	                return noRemovido.getElemento();
	            } else {
	                NoArvoreAvl noRemovido = raiz;
	                raiz.setEsquerda(this.sucessorEsquerda(raiz.getEsquerda(), raiz));
	                return noRemovido.getElemento();
	            }
				
			}
		}
		return null;	
	}
	
	private NoArvoreAvl sucessorEsquerda(NoArvoreAvl no, NoArvoreAvl noPai) {
		if(no != null) {
	        if(no.getDireita() != null) {
	            sucessorEsquerda(no.getEsquerda(), no);
	        } else {
	            if(no.getEsquerda() != null) {
	            	noPai.setDireita(no.getEsquerda());
	            	NoArvoreAvl noRemovido = no;
	                no = null;
	                return noRemovido;
	            } else {
	            	noPai.setDireita(null);
	            	NoArvoreAvl noRemovido = no;
	                no = null;
	                return noRemovido;
	            }
	        }
	        //*data = NULL;
	        NoArvoreAvl noRemovido = no;
	        return noRemovido;
	    } else {
	    	return null;
	    }
	}
	
	public ItemIndiceInvertido buscar(String palavra) {
		return this.buscar(palavra, this.raiz);
	}
	
	private ItemIndiceInvertido buscar(String palavra, NoArvoreAvl raiz) {
        ItemIndiceInvertido resultado = null;

        if(raiz != null) {
            ItemIndiceInvertido valorRaiz = raiz.getElemento();

            if (palavra.compareTo(valorRaiz.getPalavra()) < 0) {
                raiz = raiz.getEsquerda();
            } else if (palavra.compareTo(valorRaiz.getPalavra()) > 0) {
                raiz = raiz.getDireita();
            } else {
                resultado = raiz.getElemento();
            }
            resultado = this.buscar(palavra, raiz);

        }

        return resultado;
	}
	
	private NoArvoreAvl rotacaoDireita(NoArvoreAvl k2) {
		NoArvoreAvl k1 = k2.getEsquerda();
		k2.setEsquerda(k1.getDireita());
		k1.setDireita(k2);
		
		NoArvoreAvl k2Esq = k2.getEsquerda();
		NoArvoreAvl k2Dir = k2.getDireita();
		k2.setAltura(Math.max(k2Esq.getAltura(), k2Dir.getAltura()) + 1);
		
		NoArvoreAvl k1Esq = k1.getEsquerda();
		k1.setAltura(Math.max(k1Esq.getAltura(), k2.getAltura()) + 1);
		
		return k1;
	}
	
	private NoArvoreAvl rotacaoEsquerda(NoArvoreAvl k1) {
		NoArvoreAvl k2 = k1.getDireita();
		k1.setDireita(k2.getEsquerda());
		k2.setEsquerda(k1);
		
		NoArvoreAvl k1Esq = k1.getEsquerda();
		NoArvoreAvl k1Dir = k1.getDireita();
		k2.setAltura(Math.max(k1Esq.getAltura(), k1Dir.getAltura()) + 1);
		
		NoArvoreAvl k2Dir = k2.getDireita();
		k1.setAltura(Math.max(k2Dir.getAltura(), k1.getAltura()) + 1);
		
		return k2;
	}
	
	private NoArvoreAvl rotacaoDuplaDireita(NoArvoreAvl k2) {
		k2.setEsquerda(this.rotacaoEsquerda(k2.getEsquerda()));
		
		return this.rotacaoDireita(k2);
	}
	
	private NoArvoreAvl rotacaoDuplaEsquerda(NoArvoreAvl k1) {
		k1.setDireita(this.rotacaoDireita(k1.getDireita()));
		
		return this.rotacaoEsquerda(k1);
	}
}
