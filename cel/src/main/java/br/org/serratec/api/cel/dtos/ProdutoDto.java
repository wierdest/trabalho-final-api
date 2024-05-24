package br.org.serratec.api.cel.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.serratec.api.cel.config.Mapper;
import br.org.serratec.api.cel.model.Categoria;
import br.org.serratec.api.cel.model.Produto;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProdutoDto(
		Long id,
		String nome,
		String descricao,
		int qtdEstoque,
		LocalDate dataCadastro,
		BigDecimal valorUnitario,
		String imagem,
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
