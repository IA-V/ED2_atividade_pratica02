package indice;

public class ParQtdId {
	private int qtd;
	private int idProduto;
	
	public ParQtdId(int idProduto) {
		this.idProduto = idProduto;
		this.qtd = 1;
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
}
