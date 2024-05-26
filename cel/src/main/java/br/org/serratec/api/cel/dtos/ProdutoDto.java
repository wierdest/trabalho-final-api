package br.org.serratec.api.cel.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.serratec.api.cel.config.Mapper;
import br.org.serratec.api.cel.model.Categoria;
import br.org.serratec.api.cel.model.Produto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;


@JsonIgnoreProperties(ignoreUnknown = true)
public record ProdutoDto(
		Long id,

		@NotBlank(message = "O nome não pode estar em branco.")
		String nome,
		@NotBlank(message = "A descrição não pode estar em branco.")
		String descricao,
		@Min(value = 0, message = "A quantidade em estoque não pode ser negativa.")
		int qtdEstoque,
		@PastOrPresent(message = "A data de cadastro não pode ser no futuro.")
		LocalDate dataCadastro,
		@DecimalMin(value = "0.0", inclusive = false, message = "O valor unitário deve ser positivo.")

		BigDecimal valorUnitario,
	    @NotBlank(message = "A URL da imagem não pode estar vazia")
		String imagem,

		// @NotNull(message = "A categoria não pode estar em branco.")
		@Valid
		Categoria categoria
		) {

	public Produto toEntity() {
        return Mapper.getMapper().convertValue(this, Produto.class);
    }
	
	public static ProdutoDto toDto(Produto produto) {
		
		return Mapper.getMapper().convertValue(produto, ProdutoDto.class);
	}
	

}
