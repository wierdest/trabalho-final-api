 package br.org.serratec.api.cel.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.serratec.api.cel.config.Mapper;
import br.org.serratec.api.cel.model.ItemPedido;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemPedidoDto(
		Long id,
		@Min(value = 1, message = "A quantidade deve ser maior que zero.")
		int quantidade,
		@DecimalMin(value = "0.0", inclusive = false, message = "O preço de venda deve ser positivo.")
		double precoVenda,
		@DecimalMin(value = "0.0", message = "O percentual de desconto não pode ser negativo.")
        @DecimalMax(value = "100.0", message = "O percentual de desconto não pode ser superior a 100%.")
		double percentualDesconto,
		@DecimalMin(value = "0.0", inclusive = false, message = "O valor bruto deve ser positivo.")
		double valorBruto,
        @DecimalMin(value = "0.0", inclusive = false, message = "O valor líquido deve ser positivo.")
		double valorLiquido,
		@NotNull(message = "O pedido deverá ser selecionado.")
		PedidoDto pedido,
		@NotNull(message = "O produto deverá ser selecionado.")
		ProdutoDto produto
		) {
	
	  
	  public ItemPedido toEntity() {
	        return Mapper.getMapper().convertValue(this, ItemPedido.class);
	    }
	  
	  public static ItemPedidoDto toDto(ItemPedido itemPedido) {
		  return Mapper.getMapper().convertValue(itemPedido, ItemPedidoDto.class);
	  }
	  
	  public ItemRelatorioDTO toItemRelatorio() {
		  return new ItemRelatorioDTO(
				  this.id,
				  this.produto.nome(),
				  this.precoVenda,
				  this.quantidade,
				  this.valorBruto,
				  this.percentualDesconto,
				  this.valorLiquido
				  );
	  }

}
