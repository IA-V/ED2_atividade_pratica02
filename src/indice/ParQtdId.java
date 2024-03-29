package indice;

public class ParQtdId {
	private int qtd;
	private int idProduto;
	private Double relevancia;
	
	public ParQtdId(int idProduto) {
		this.idProduto = idProduto;
		this.qtd = 1;
	}

	@Override
	public String toString() {
		return "ParQtdId [idProduto=" + idProduto + ", qtd=" + qtd + ", relevancia=" + relevancia + "]";
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	
	public void incrementarQtd(int qtd) {
		this.qtd += qtd;
	}

	public Double getRelevancia() {
		return relevancia;
	}

	public void setRelevancia(Double relevancia) {
		this.relevancia = relevancia;
	}

}
