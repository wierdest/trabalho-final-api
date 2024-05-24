package br.org.serratec.api.cel.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.serratec.api.cel.config.Mapper;
import br.org.serratec.api.cel.model.Categoria;
import br.org.serratec.api.cel.model.Produto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProdutoDto(
		Long id,
	    @NotBlank(message = "O nome de usuário não pode estar em branco")
		String nome,
	    @NotBlank(message = "A descrição não pode estar em branco")
		String descricao,
		@Min(value = 1, message = "A quantidade de estoque deve ser no minimo 1 produto")
		int qtdEstoque,
	    @FutureOrPresent(message = "A data não pode estar no passado")
		LocalDate dataCadastro,
		@DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
	    @DecimalMax(value = "10000.0", message = "O preço deve ser menor ou igual a 10000")
		BigDecimal valorUnitario,
	    @NotBlank(message = "A URL da imagem não pode estar vazia")
		String imagem,
		@NotNull(message = "A categoria não pode estar em branco")
	    @Valid
		Categoria categoria
		) {

	public Produto toEntity() {
        return Mapper.getMapper().convertValue(this, Produto.class);
    }
	
	public static ProdutoDto toDto(Produto produto) {
		ProdutoDto produtoDto = new ProdutoDto(
				produto.getId(),			
				produto.getNome(),
				produto.getDescricao(),
				produto.getQtdEstoque(),
				produto.getDataCadastro(),
				produto.getValorUnitario(),
				produto.getImagem(),
				produto.getCategoria()		
				);	
		
		return produtoDto;
	}

}
