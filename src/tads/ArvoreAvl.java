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
    
    public void listar() {
    	this.listar(this.raiz);
    }
    
    private void listar(NoArvoreAvl raiz) {
    	if(raiz != null) {
    		this.listar(raiz.getEsquerda());
    		
    		System.out.println("Pai da Raiz = "+raiz.getPai());
        	System.out.println("Raiz = "+raiz.getChave());
        	//System.out.println("FB = " + (raiz.getDireita().getAltura()-raiz.getEsquerda().getAltura()));
        	System.out.println("filho esq da raiz = "+raiz.getEsquerda());
        	System.out.println("filho dir da raiz = "+raiz.getDireita());
        	System.out.println();
        	
        	this.listar(raiz.getDireita());
    	}
    	
    	
    }
    
    public void inserir(ItemIndiceInvertido item) {
		NoArvoreAvl novoNo = new NoArvoreAvl(item);
		this.inserir(this.raiz, novoNo);
	}

	public void inserir(NoArvoreAvl raiz, NoArvoreAvl novoNo) {

		if (raiz == null) {
			this.raiz = novoNo;

		} else {

			if (novoNo.getChave().compareTo(raiz.getChave()) < 0) {

				if (raiz.getEsquerda() == null) {
					raiz.setEsquerda(novoNo);
					novoNo.setPai(raiz);
					this.balancear(raiz);

				} else {
					this.inserir(raiz.getEsquerda(), novoNo);
				}

			} else if (novoNo.getChave().compareTo(raiz.getChave()) > 0) {

				if (raiz.getDireita() == null) {
					raiz.setDireita(novoNo);
					novoNo.setPai(raiz);
					this.balancear(raiz);

				} else {
					this.inserir(raiz.getDireita(), novoNo);
				}

			} else {
				// O nó já existe na árvore
			}
		}
	}
    
    public ItemIndiceInvertido remover(String chave) {
		return removerAVL(this.raiz, chave);
	}

	public ItemIndiceInvertido removerAVL(NoArvoreAvl raiz, String chave) {
		if(raiz == null) {
			return null;
		} else {
			if (raiz.getChave().compareTo(chave) > 0) {
				return removerAVL(raiz.getEsquerda(), chave);

			} else if (raiz.getChave().compareTo(chave) < 0) {
				return removerAVL(raiz.getDireita(), chave);

			} else /*if (raiz.getChave().compareTo(chave) == 0)*/ {
				return removerNoEncontrado(raiz);
			}
		}
	}

	public ItemIndiceInvertido removerNoEncontrado(NoArvoreAvl noRemovido) {
		ItemIndiceInvertido itemRemovido = noRemovido.getElemento();
		NoArvoreAvl noAux1;

		if(noRemovido.getEsquerda() == null || noRemovido.getDireita() == null) {

			if(noRemovido.getPai() == null) {
				this.raiz = null;
				noRemovido = null;
				return null;
			}
			noAux1 = noRemovido;

		} else {
			noAux1 = this.getSucessor(noRemovido);
			noRemovido.setChave(noAux1.getChave());
		}

		NoArvoreAvl noAux2;
		if(noAux1.getEsquerda() != null) {
			noAux2 = noAux1.getEsquerda();
		} else {
			noAux2 = noAux1.getDireita();
		}

		if(noAux2 != null) {
			noAux2.setPai(noAux1.getPai());
		}

		if(noAux1.getPai() == null) {
			this.raiz = noAux2;
		} else {
			if(noAux1 == noAux1.getPai().getEsquerda()) {
				noAux1.getPai().setEsquerda(noAux2);
			} else {
				noAux1.getPai().setDireita(noAux2);
			}
			balancear(noAux1.getPai());
		}
		noAux1 = null;
		
		return itemRemovido;
	}
	
	public void balancear(NoArvoreAvl raiz) {
		int fb = this.calcularAltura(raiz.getDireita()) - this.calcularAltura(raiz.getEsquerda());

		if(fb <= -2) {

			if(this.calcularAltura(raiz.getEsquerda().getEsquerda()) >= this.calcularAltura(raiz.getEsquerda().getDireita())) {
				raiz = rotacaoDireita(raiz);

			} else {
				raiz = rotacaoDuplaDireita(raiz);
			}

		} else if(fb >= 2) {

			if (this.calcularAltura(raiz.getDireita().getDireita()) >= this.calcularAltura(raiz.getDireita().getEsquerda())) {
				raiz = rotacaoEsquerda(raiz);

			} else {
				raiz = rotacaoDuplaEsquerda(raiz);
			}
		}

		if(raiz.getPai() != null) {
			balancear(raiz.getPai());
		} else {
			this.raiz = raiz;
		}
	}
    
    private int calcularAltura(NoArvoreAvl no) {
    	if (no == null) {
			return -1;
		}

		if (no.getEsquerda() == null && no.getDireita() == null) {
			return 0;
		
		} else if (no.getEsquerda() == null) {
			return 1 + this.calcularAltura(no.getDireita());
		
		} else if (no.getDireita() == null) {
			return 1 + this.calcularAltura(no.getEsquerda());
		
		} else {
			return 1 + Math.max(this.calcularAltura(no.getEsquerda()), this.calcularAltura(no.getDireita()));
		}
	}
	
	private NoArvoreAvl getSucessor(NoArvoreAvl no) {
		if (no.getDireita() != null) {
			NoArvoreAvl noAux1 = no.getDireita();
			while (noAux1.getEsquerda() != null) {
				noAux1 = noAux1.getEsquerda();
			}
			return noAux1;
		} else {
			NoArvoreAvl noAux2 = no.getPai();
			while (noAux2 != null && no == noAux2.getDireita()) {
				no = noAux2;
				noAux2 = no.getPai();
			}
			return noAux2;
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
	
	/*private NoArvoreAvl rotacaoDireita(NoArvoreAvl k2) {
		NoArvoreAvl k1 = k2.getEsquerda();
		k2.setEsquerda(k1.getDireita());
		k1.setDireita(k2);
		
		NoArvoreAvl k2Esq = k2.getEsquerda();
		NoArvoreAvl k2Dir = k2.getDireita();
		k2.setAltura(Math.max(this.calcularAltura(k2Esq), this.calcularAltura(k2Dir)) + 1);
		
		NoArvoreAvl k1Esq = k1.getEsquerda();
		k1.setAltura(Math.max(this.calcularAltura(k1Esq), this.calcularAltura(k2)) + 1);
		
		return k1;
	}*/
	
	public NoArvoreAvl rotacaoDireita(NoArvoreAvl no) {

		NoArvoreAvl esq = no.getEsquerda();
		esq.setPai(no.getPai());

		no.setEsquerda(esq.getDireita());

		if (no.getEsquerda() != null) {
			no.getEsquerda().setPai(no);
		}

		esq.setDireita(no);
		no.setPai(esq);

		if (esq.getPai() != null) {

			if (esq.getPai().getEsquerda() == no) {
				esq.getPai().setDireita(esq);
			
			} else if (esq.getPai().getDireita() == no) {
				esq.getPai().setEsquerda(esq);
			}
		}

		return esq;
	}
	
	public NoArvoreAvl rotacaoEsquerda(NoArvoreAvl no) {

		NoArvoreAvl dir = no.getDireita();
		dir.setPai(no.getPai());

		no.setDireita(dir.getEsquerda());

		if (no.getDireita() != null) {
			no.getDireita().setPai(no);
		}

		dir.setEsquerda(no);
		no.setPai(dir);

		if (dir.getPai() != null) {

			if (dir.getPai().getDireita() == no) {
				dir.getPai().setDireita(dir);
			
			} else if (dir.getPai().getEsquerda() == no) {
				dir.getPai().setEsquerda(dir);
			}
		}

		return dir;
	}
	
	/*private NoArvoreAvl rotacaoEsquerda(NoArvoreAvl k1) {
		NoArvoreAvl k2 = k1.getDireita();
		k1.setDireita(k2.getEsquerda());
		k2.setEsquerda(k1);
		
		NoArvoreAvl k1Esq = k1.getEsquerda();
		NoArvoreAvl k1Dir = k1.getDireita();
		k2.setAltura(Math.max(this.calcularAltura(k1Esq), this.calcularAltura(k1Dir)) + 1);
		
		NoArvoreAvl k2Dir = k2.getDireita();
		k1.setAltura(Math.max(this.calcularAltura(k2Dir), this.calcularAltura(k1)) + 1);
		
		return k2;
	}*/
	
	private NoArvoreAvl rotacaoDuplaDireita(NoArvoreAvl no) {
		no.setEsquerda(rotacaoEsquerda(no.getEsquerda()));
		return this.rotacaoDireita(no);
	}
	
	private NoArvoreAvl rotacaoDuplaEsquerda(NoArvoreAvl no) {
		no.setDireita(rotacaoDireita(no.getDireita()));
		return rotacaoEsquerda(no);
	}
}
