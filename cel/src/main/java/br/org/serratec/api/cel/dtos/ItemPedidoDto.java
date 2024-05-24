package br.org.serratec.api.cel.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.serratec.api.cel.config.Mapper;
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
		PedidoDto pedido,
		ProdutoDto produto
		) {
	
	  
	  public ItemPedido toEntity() {
	        return Mapper.getMapper().convertValue(this, ItemPedido.class);
	    }
	  
	  public static ItemPedidoDto toDto(ItemPedido itemPedido) {
		  return Mapper.getMapper().convertValue(itemPedido, ItemPedidoDto.class);
	  }

}
