package br.org.serratec.api.cel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.api.cel.dtos.ProdutoDto;
import br.org.serratec.api.cel.model.Categoria;
import br.org.serratec.api.cel.model.Produto;
import br.org.serratec.api.cel.repository.CategoriaRepository;
import br.org.serratec.api.cel.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	ProdutoRepository repositorio;
	
	@Autowired
    private CategoriaRepository categoriaRepositorio;
	
	public List<ProdutoDto> obterTodos() {
		return repositorio.findAll()
				.stream().map(p -> ProdutoDto.toDto(p)).toList();
	}
	
	public Optional<ProdutoDto> obterPorId(Long id){
		Optional<Produto> produtoEntity = repositorio.findById(id);
		if(produtoEntity.isPresent()) {
			return Optional.of(ProdutoDto.toDto(produtoEntity.get()));
		}
		return Optional.empty();
	}
	
	public ProdutoDto cadastrar(ProdutoDto produto) {
		Produto produtoEntity = produto.toEntity();	

        if (repositorio.existsByDescricao(produto.descricao())) {
            throw new IllegalArgumentException("Já existe um produto com essa descrição");
        }

        // 
        Optional<Categoria> categoriaOptional = categoriaRepositorio.findByNome(produto.categoria().getNome());
        Categoria categoria;

        if (categoriaOptional.isPresent()) {
            // Se a categoria existir, usar a categoria existente
            categoria = categoriaOptional.get();
        } else {
            // Se a categoria não existir, criar uma nova categoria
            categoria = new Categoria();
            categoria.setNome(produto.categoria().getNome());
            categoria.setDescricao(produto.categoria().getDescricao());
            categoria = categoriaRepositorio.save(categoria);
        }

        // Criar a entidade Produto e associar a categoria
        produtoEntity.setCategoria(categoria);


		return ProdutoDto.toDto(repositorio.save(produtoEntity));
	}
	
	
	public Optional<ProdutoDto> atualizar(Long id,ProdutoDto produto){
		if(repositorio.existsById(id)) {
			Produto produtoEntity = produto.toEntity();
			produtoEntity.setId(id);
			repositorio.save(produtoEntity);
			return Optional.of(ProdutoDto.toDto(produtoEntity));
		}
		
		return Optional.empty();
	}
	
	public boolean deletar(Long id) {
		Optional<Produto> produto = repositorio.findById(id);
		if(produto.isEmpty()){
			return false;
		}
		repositorio.deleteById(id);
		return true;
	}
}
