package indice;

import java.util.ArrayList;

public class ItemIndiceInvertido {
	private String palavra;
	private ArrayList<ParQtdId> parQtdId; 
	
	public ItemIndiceInvertido (String palavra) {
		this.palavra = palavra;
		this.parQtdId = new ArrayList<>();
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public ArrayList<ParQtdId> getParQtdId() {
		return parQtdId;
	}

	public void setParQtdId(ArrayList<ParQtdId> parQtdId) {
		this.parQtdId = parQtdId;
	}
}
