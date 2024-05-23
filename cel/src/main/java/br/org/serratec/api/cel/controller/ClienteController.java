package br.org.serratec.api.cel.controller;

pedidoquasela
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.api.cel.model.Cliente;

import br.org.serratec.api.cel.service.PedidoService;

@RestController
@RequestMapping("/cliente")

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

import br.org.serratec.api.cel.dtos.ClienteDTO;
import br.org.serratec.api.cel.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
 main
public class ClienteController {
	
	@Autowired
	PedidoService service;
	
	@PostMapping
	public Cliente cadastrarCliente(@RequestBody Cliente cliente) {
		return service.cadastrarPedido(cliente);
	}

	@Autowired
	private ClienteService servico;
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> obterTodos() {
		return new ResponseEntity<>(servico.obterTodos(), HttpStatus.OK);
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
		return new ResponseEntity<>(servico.cadastraCliente(cliente), HttpStatus.CREATED);
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