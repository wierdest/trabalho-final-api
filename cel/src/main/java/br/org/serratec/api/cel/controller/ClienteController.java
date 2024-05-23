package br.org.serratec.api.cel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import br.org.serratec.api.cel.model.Cliente;

import br.org.serratec.api.cel.service.PedidoService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	PedidoService service;
	
	@PostMapping
	public Cliente cadastrarCliente(@RequestBody Cliente cliente) {
		return service.cadastrarPedido(cliente);
	}

}
