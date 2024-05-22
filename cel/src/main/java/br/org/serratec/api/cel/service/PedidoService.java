package br.org.serratec.api.cel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.api.cel.dtos.ViaCEPDTO;
import br.org.serratec.api.cel.model.Pedido;
import br.org.serratec.api.cel.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	ConverteJSON conversorJSON;
	
	@Autowired
	PedidoRepository repositorio;
	
	
	public ViaCEPDTO conferirCep(String cep) {
		var json = ViaCEPService.obterDados(cep);
		return conversorJSON.converter(json, ViaCEPDTO.class);
	}


	public List<Pedido> obterTodos() {
		return repositorio.findAll();
	}


	public Pedido cadastrarPedido(Pedido pedido) {
		return repositorio.save(pedido);
	}

}
