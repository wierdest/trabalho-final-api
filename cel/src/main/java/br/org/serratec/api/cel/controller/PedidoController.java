package br.org.serratec.api.cel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.api.cel.dtos.PedidoDto;
import br.org.serratec.api.cel.model.Endereco;
import br.org.serratec.api.cel.model.Pedido;
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
	
	@PostMapping
	public ResponseEntity<PedidoDto> cadastrarPedido(@Valid @RequestBody PedidoDto pedido){
		return new ResponseEntity<PedidoDto>(servico.cadastrarPedido(pedido), HttpStatus.CREATED);
	}
	
}
