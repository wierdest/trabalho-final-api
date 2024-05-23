package br.org.serratec.api.cel.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.serratec.api.cel.model.ItemPedido;
import br.org.serratec.api.cel.model.Pedido;
import br.org.serratec.api.cel.model.Produto;
@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemPedidoDto(
		Long id,
		int quantidade,
		double precoVenda,
		int percentualDesconto,
		double valorBruto,
		double valorLiquido,
		Pedido pedido,
		Produto produto
		) {
	
	  public ItemPedido toEntity() {
	        return new ItemPedido(
	                this.id,
	                this.quantidade,
	                this.precoVenda,
	                this.percentualDesconto,
	                this.valorBruto,
	                this.valorLiquido,
	                this.pedido,
	                this.produto
	        );
	    }

}
