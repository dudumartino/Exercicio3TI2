package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Carro implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String DESCRICAO_PADRAO = "Novo Carro";
	public static final int MAX_ESTOQUE = 1000;
	private int id;
	private String descricao;
	private float preco;
	private int quantidade;
	private LocalDateTime dataFabricacao;	
	/*validade nao*/
	
	public Carro() {
		id = -1;
		descricao = DESCRICAO_PADRAO;
		preco = 0.01F;
		quantidade = 0;
		dataFabricacao = LocalDateTime.now();
		/*validade nao*/
	}

	public Carro(int id, String descricao, float preco, int quantidade, LocalDateTime fabricacao) {
		setId(id);
		setDescricao(descricao);
		setPreco(preco);
		setQuant(quantidade);
		setDataFabricacao(fabricacao);
		/*validade nao*/
	}		
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		if (descricao.length() >= 3)
			this.descricao = descricao;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		if (preco > 0)
			this.preco = preco;
	}

	public int getQuant() {
		return quantidade;
	}
	
	public void setQuant(int quantidade) {
		if (quantidade >= 0 && quantidade <= MAX_ESTOQUE)
			this.quantidade = quantidade;
	}

	public LocalDateTime getDataFabricacao() {
		return dataFabricacao;
	}

	public void setDataFabricacao(LocalDateTime dataFabricacao) {
		// Pega a Data Atual
		LocalDateTime agora = LocalDateTime.now();
		// Garante que a data de fabricação não pode ser futura
		if (agora.compareTo(dataFabricacao) >= 0)
			this.dataFabricacao = dataFabricacao;
	}


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Produto: " + descricao + "   Preço: R$" + preco + "   Quant.: " + quantidade + "   Fabricação: "
				+ dataFabricacao;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Carro) obj).getId());
	}
}
