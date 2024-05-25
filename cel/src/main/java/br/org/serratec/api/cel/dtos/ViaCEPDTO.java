package br.org.serratec.api.cel.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.serratec.api.cel.config.Mapper;
import br.org.serratec.api.cel.model.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ViaCEPDTO(
		@NotBlank(message = "Digite um CEP válido de 8 dígitos.")
	    @Pattern(regexp = "\\d{8}")
		String cep,
		@NotBlank(message = "É necessário preencher o logradouro.")
		String logradouro,
		@NotBlank(message = "Complemento obrigatório.")
		String complemento,
		@NotBlank(message = "O bairro precisa ser informado.")
		String bairro,
		@NotBlank(message = "O cidade precisa ser informada.")
		String localidade,
		@NotBlank(message = "A UF precisa ser informada.")
		String uf,
		@NotBlank
		@Pattern(regexp = "\\d{2}", message = "O DDD deve conter exatamente 2 dígitos")
		String ddd
		
		) {
	 
	public Endereco toEntity() {
		return Mapper.getMapper().convertValue(this,  Endereco.class);
	}
	
	public static ViaCEPDTO toDTO(Endereco entity) {
		return Mapper.getMapper().convertValue(entity,  ViaCEPDTO.class);

	}
	
}
