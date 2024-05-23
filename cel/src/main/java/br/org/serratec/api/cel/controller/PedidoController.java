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


import br.org.serratec.api.cel.dtos.PedidoDto;
import br.org.serratec.api.cel.model.Endereco;
import br.org.serratec.api.cel.service.PedidoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/celulares")
public class PedidoController {
	
	@Autowired
	PedidoService servico;
	
	@GetMapping("/teste-endereco/{cep}")
	public ResponseEntity<Endereco> conferirDadosViaCep(@PathVariable String cep) {
		
		return ResponseEntity.ok(servico.conferirCep(cep).get());
	}
	
	@GetMapping
	public ResponseEntity<List<PedidoDto>> obterTodos() {
		return ResponseEntity.ok(servico.obterTodos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PedidoDto> obterPedidoPorId(@PathVariable Long id) {
		Optional<PedidoDto> pedidoDto = servico.obterPedidoPorId(id);
		if(pedidoDto.isPresent()) {
			return new ResponseEntity<>(pedidoDto.get(), HttpStatus.FOUND);
		}		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
	}
	
	
	@PostMapping
	public ResponseEntity<PedidoDto> cadastrarPedido(@Valid @RequestBody PedidoDto pedido){
		return new ResponseEntity<PedidoDto>(servico.cadastrarPedido(pedido), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PedidoDto> atulizarPedido(@PathVariable Long id, @RequestBody @Valid PedidoDto pedido){
		Optional<PedidoDto> pedidoDto = servico.atulizarPedido(id, pedido);
		if(pedidoDto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(pedidoDto.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarPedido(@PathVariable Long id){
		if(!servico.deletarPedido(id)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
}
