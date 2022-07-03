package indice;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemIndiceInvertido {
	private String palavra;
	private HashMap<Integer, Integer> paresQtdId;
	private Double relevancia;
	
	public ItemIndiceInvertido (String palavra) {
		this.palavra = palavra;
		this.paresQtdId = new HashMap<>();
	}

	public Double getRelevancia() {
		return relevancia;
	}

	public void setRelevancia(Double relevancia) {
		this.relevancia = relevancia;
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public HashMap<Integer, Integer> getParQtdId() {
		return paresQtdId;
	}

	public void setParQtdId(HashMap<Integer, Integer> parQtdId) {
		this.paresQtdId = parQtdId;
	}
	
	public void addParQtdId(Integer idProduto, Integer qtd) {
		this.paresQtdId.put(idProduto, qtd);
	}
	
	/*public void incrementarQtd(int incremento, int idProduto) {
		for(ParQtdId par: this.paresQtdId) {
			int idProdutoPar = par.getIdProduto();
			
			if(idProdutoPar == idProduto) {
				par.incrementarQtd(incremento);
				break;
			}
			
		}
	}*/

	@Override
	public String toString() {
		return "[palavra=" + palavra + ", relevancia=" + relevancia
				+ "]";
	}

	
}
