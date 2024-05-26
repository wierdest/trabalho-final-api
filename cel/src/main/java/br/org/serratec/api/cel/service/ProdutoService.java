package br.org.serratec.api.cel.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		return repositorio.findAll().stream().map(p -> ProdutoDto.toDto(p)).toList();
	}

	public Page<ProdutoDto> obterTodos(Pageable pageable) {
		Page<ProdutoDto> produtos = repositorio.findAll(pageable).map(c ->
		ProdutoDto.toDto(c));
		return produtos;
	}
	

	public Optional<ProdutoDto> obterPorId(Long id){
		Optional<Produto> produtoEntity = repositorio.findById(id);
		if(produtoEntity.isPresent()) {
			return Optional.of(ProdutoDto.toDto(produtoEntity.get()));
		}
		return Optional.empty();
	}

	public Produto buscarProdutoPorIdPedido(Long id) {
        return repositorio.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
	
	public ProdutoDto cadastrar(ProdutoDto produto) {
		
		Produto produtoEntity = produto.toEntity();	

        if (repositorio.existsByDescricao(produto.descricao())) {
            throw new IllegalArgumentException("Já existe um produto com essa descrição");
        }

        Optional<Categoria> categoriaOptional = categoriaRepositorio.findByNomeIgnoreCase(produto.categoria().getNome());
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

        Optional<Produto> produtoNoRepositorio = repositorio.findById(id);

        if(produtoNoRepositorio.isPresent()) {

            Categoria categoria = categoriaRepositorio.findById(id).orElseThrow();
            Produto produtoEntity = produto.toEntity();
            produtoEntity.setId(id);
            produtoEntity.setCategoria(categoria);
            repositorio.save(produtoEntity);

            return Optional.of(ProdutoDto.toDto(produtoEntity));
        }

        throw new IllegalArgumentException("Id do produto é inválida!!");
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
