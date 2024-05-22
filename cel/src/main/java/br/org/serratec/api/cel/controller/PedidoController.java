package br.org.serratec.api.cel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.api.cel.dtos.ViaCEPDTO;
import br.org.serratec.api.cel.model.Pedido;
import br.org.serratec.api.cel.service.PedidoService;

@RestController
@RequestMapping("/celulares")
public class PedidoController {
	
	@Autowired
	PedidoService servico;
	
	@GetMapping("/teste-cep/{cep}")
	public ViaCEPDTO conferirDadosViaCep(@PathVariable String cep) {
		return servico.conferirCep(cep);
	}
	
	@GetMapping
	public List<Pedido> obterTodos() {
		return servico.obterTodos();
	}
	
	@PostMapping
	public Pedido cadastrarPedido(@RequestBody Pedido pedido){
		return servico.cadastrarPedido(pedido);
	}
	
}
