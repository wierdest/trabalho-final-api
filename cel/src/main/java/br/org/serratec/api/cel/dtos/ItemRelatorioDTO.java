package br.org.serratec.api.cel.dtos;

public record ItemRelatorioDTO (
		Long codigo,
		String nomeProduto,
		Double precoVenda,
		int quantidade,
		Double valorBruto,
		Double percentualDesconto,
		Double valorLiquido
		) {

}
