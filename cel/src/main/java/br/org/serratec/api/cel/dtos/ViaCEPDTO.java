package br.org.serratec.api.cel.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record ViaCEPDTO(
		String cep,
		String logradouro,
		String complemento,
		String bairro,
		String localidade,
		String uf,
		String ddd
		
		) {
	
}
