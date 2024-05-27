package br.org.serratec.api.cel.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.serratec.api.cel.config.Mapper;
import br.org.serratec.api.cel.model.Pedido;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PedidoDto(
		Long id,
		// @NotNull
        // @PastOrPresent(message = "A data do pedido não pode ser retroativa à data atual.")
		LocalDate dataPedido,
		// @NotNull
        // @Future(message = "A data da entrega deve ser posterior à data atual.")
		LocalDate dataEntrega,
		@NotBlank(message = "O status não pode estar em branco.")
		String status,
		// @DecimalMin(value = "0.0", inclusive = false, message = "Digite um valor positivo válido.")
		double valorTotal,
		@NotNull(message = "Digite o nome do cliente.")
		ClienteDTO cliente,
		@NotBlank
		String descricao,
		List<ItemPedidoDto> itensPedido) {
	
	public Pedido toEntity() {
        return Mapper.getMapper().convertValue(this, Pedido.class);
    }
	
	public static PedidoDto toDto(Pedido pedido) {
		return Mapper.getMapper().convertValue(pedido, PedidoDto.class);
    }
	
	public RelatorioPedidoDTO toRelatorio() {
		
		List<ItemRelatorioDTO> itensRelatorio = new ArrayList<>();
		
		this.itensPedido.forEach(i -> {
			itensRelatorio.add(i.toItemRelatorio());
		});
		  
		return new RelatorioPedidoDTO(
				this.id,
				this.dataPedido,
				this.dataEntrega,
				this.valorTotal,
				itensRelatorio
				);	
	}

}
