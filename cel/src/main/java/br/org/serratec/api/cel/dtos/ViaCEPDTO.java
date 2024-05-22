package br.org.serratec.api.cel.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.serratec.api.cel.model.Endereco;

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
	 
	public Endereco toEntity() {
		return new Endereco(
				this.cep, 
				this.logradouro, 
				this.complemento, 
				this.bairro, 
				this.localidade, 
				this.uf);
				
	}
	
}
