package br.org.serratec.api.cel.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.serratec.api.cel.config.Mapper;
import br.org.serratec.api.cel.model.Cliente;
import br.org.serratec.api.cel.model.ItemPedido;
import br.org.serratec.api.cel.model.Pedido;
@JsonIgnoreProperties(ignoreUnknown = true)
public record PedidoDto(
		Long id,
		LocalDate dataPedido,
		LocalDate dataEntrega,
		String status,
		double valorTotal,
		ClienteDTO cliente,
		String descricao,
		List<ItemPedidoDto> itemPedido) {
	

	

	public Pedido toEntity() {
        return Mapper.getMapper().convertValue(this, Pedido.class);
    }
	
	public static PedidoDto toDto(Pedido pedido) {
		List<ItemPedidoDto> itens = new ArrayList<ItemPedidoDto>();
		pedido.getItemPedido().forEach(i -> {
			itens.add(ItemPedidoDto.toDto(i));
		});
		
		PedidoDto pedidoDto = new PedidoDto(
				pedido.getId(),			
				pedido.getDataPedido(),
				pedido.getDataEntrega(),
				pedido.getStatus(),
				pedido.getValorTotal(),
				ClienteDTO.toDto(pedido.getCliente()),
				pedido.getDescricao(),
				itens			
				);	
		
		return pedidoDto;
	}

}
