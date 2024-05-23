package br.org.serratec.api.cel.dtos;

import java.time.LocalDate;
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
		Cliente cliente,
		String descricao,
		List<ItemPedidoDto> itemPedido) {
	

	

	public Pedido toEntity() {
        return Mapper.getMapper().convertValue(this, Pedido.class);
    }
	
	public static PedidoDto toDto(Pedido pedido) {
		return Mapper.getMapper().convertValue(pedido, PedidoDto.class);	
	}

}
