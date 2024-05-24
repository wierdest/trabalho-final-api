package br.org.serratec.api.cel.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.api.cel.dtos.ProdutoDto;
import br.org.serratec.api.cel.service.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	ProdutoService servico;
	
	@GetMapping
	public ResponseEntity<List<ProdutoDto>> obterTodos() {
		return ResponseEntity.ok(servico.obterTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDto> obterProdutoPorId(@PathVariable Long id) {
		Optional<ProdutoDto> produtoDto = servico.obterPorId(id);
		if(produtoDto.isPresent()) {
			return new ResponseEntity<>(produtoDto.get(), HttpStatus.FOUND);
		}		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
	}
	
	
	@PostMapping
	public ResponseEntity<ProdutoDto> cadastrarProduto(@Valid @RequestBody ProdutoDto produto){
		return new ResponseEntity<ProdutoDto>(servico.cadastrar(produto), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDto> atulizarProduto(@PathVariable Long id, @RequestBody @Valid ProdutoDto produto){
		Optional<ProdutoDto> produtoDto = servico.atualizar(id, produto);
		if(produtoDto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtoDto.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id){
		if(!servico.deletar(id)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
