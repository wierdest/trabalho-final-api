package br.org.serratec.api.cel.dtos;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public record RelatorioPedidoDTO(
		Long idPedido,
		LocalDate dataPedido,
		Double valorTotal,
		List<ItemRelatorioDTO> itens
		) {
	
	
}
