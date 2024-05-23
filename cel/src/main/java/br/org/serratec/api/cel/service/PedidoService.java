package br.org.serratec.api.cel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.api.cel.dtos.PedidoDto;
import br.org.serratec.api.cel.dtos.ViaCEPDTO;
import br.org.serratec.api.cel.model.Cliente;
import br.org.serratec.api.cel.model.Endereco;
import br.org.serratec.api.cel.model.Pedido;
import br.org.serratec.api.cel.repository.ClienteRepository;
import br.org.serratec.api.cel.repository.PedidoRepository;
import jakarta.validation.Valid;

@Service
public class PedidoService {
	
	@Autowired
	ConverteJSON conversorJSON;
	
	@Autowired
	PedidoRepository pedidoRepositorio;
	
	@Autowired
	ClienteRepository clienteRepositorio;
	
	
	public Optional<Endereco> conferirCep(String cep) {
		var json = ViaCEPService.obterDados(cep);
		ViaCEPDTO dto = conversorJSON.converter(json, ViaCEPDTO.class);
		// confere se os dados obtidos pelo cep são funcionais
		
		// idealmente confere junto ao database, para saber se não há
		// algum dado repetido e tal
		
		Optional<Endereco> enderecoEntity = Optional.of(dto.toEntity());
		
		// salvar no repository do cliente
		
		
		return enderecoEntity;
	}


	public List<PedidoDto> obterTodos() {
		return pedidoRepositorio.findAll()
				.stream().map(p -> PedidoDto.toDto(p)).toList();
	}


	public PedidoDto cadastrarPedido(PedidoDto pedido) {
		Pedido pedidoEntity = pedido.toEntity();
		pedidoRepositorio.save(pedidoEntity);
		return PedidoDto.toDto(pedidoEntity);
	}
	
	public Cliente cadastrarPedido(Cliente cliente) {
		return clienteRepositorio.save(cliente);
			
	}

}
