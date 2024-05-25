package br.org.serratec.api.cel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;


import br.org.serratec.api.cel.dtos.ClienteDTO;
import br.org.serratec.api.cel.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	

	@Autowired
	private ClienteService servico;
	
	@GetMapping
	public ResponseEntity<Page<ClienteDTO>> obterTodos(
			@PageableDefault(size=2, page=0, sort="nome", direction=Sort.Direction.ASC) Pageable pageable
			) {
		return new ResponseEntity<>(servico.obterTodos(pageable), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> obterPorId(@PathVariable Long id) {
		Optional<ClienteDTO> dto = servico.obterClientePorId(id);
		if (dto.isPresent()) {
			return new ResponseEntity<>(dto.get(), HttpStatus.FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTO> cadastrarCliente(@RequestBody @Valid ClienteDTO cliente) {
		
		
		return new ResponseEntity<>(servico.cadastraOuAcessaCliente(cliente), HttpStatus.CREATED);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteDTO cliente) {
		Optional<ClienteDTO> dto = servico.atualizarCliente(id, cliente);
		
		if (dto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}		
		return ResponseEntity.ok(dto.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		if(!servico.excluirCliente(id)){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}
	
	

}