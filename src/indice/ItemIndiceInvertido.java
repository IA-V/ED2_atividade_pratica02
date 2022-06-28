package indice;

import java.util.ArrayList;

public class ItemIndiceInvertido {
	private String palavra;
	private ArrayList<ParQtdId> paresQtdId; 
	
	public ItemIndiceInvertido (String palavra) {
		this.palavra = palavra;
		this.paresQtdId = new ArrayList<>();
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public ArrayList<ParQtdId> getParQtdId() {
		return paresQtdId;
	}

	public void setParQtdId(ArrayList<ParQtdId> parQtdId) {
		this.paresQtdId = parQtdId;
	}
	
	public void addParQtdId(ParQtdId novoPar) {
		this.paresQtdId.add(novoPar);
	}
	
	public void incrementarQtd(int incremento, int idProduto) {
		for(ParQtdId par: this.paresQtdId) {
			int idProdutoPar = par.getIdProduto();
			
			if(idProdutoPar == idProduto) {
				par.incrementarQtd(incremento);
				break;
			}
			
		}
	}
}
