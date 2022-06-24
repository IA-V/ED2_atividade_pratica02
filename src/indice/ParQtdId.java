package indice;

public class ParQtdId {
	private int qtd;
	private int idProduto;
	
	public ParQtdId(int idProduto) {
		this.idProduto = idProduto;
		this.qtd = 0;
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
}
