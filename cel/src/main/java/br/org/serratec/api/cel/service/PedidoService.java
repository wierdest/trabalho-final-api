package br.org.serratec.api.cel.service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.api.cel.dtos.ClienteDTO;

import br.org.serratec.api.cel.dtos.PedidoDto;
import br.org.serratec.api.cel.dtos.ViaCEPDTO;

import br.org.serratec.api.cel.model.Endereco;
import br.org.serratec.api.cel.model.ItemPedido;
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
	
	@Autowired
	ClienteService clienteService;

	
	
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
                .stream().map(PedidoDto::toDto).toList();
    }
	
	public Optional<PedidoDto> obterPedidoPorId(Long id){
		Optional<Pedido> pedidoEntity = pedidoRepositorio.findById(id);
		if(pedidoEntity.isPresent()) {
			return Optional.of(PedidoDto.toDto(pedidoEntity.get()));
		}
		return Optional.empty();
	}


	public PedidoDto cadastrarPedido(PedidoDto pedido) {

        ClienteDTO cliente = clienteService.cadastraCliente(pedido.cliente());

        Pedido pedidoACadastrar = pedido.toEntity();

        pedidoACadastrar.setCliente(cliente.toEntity());

        List<ItemPedido> itensPedido = new ArrayList<>();

        pedido.itemPedido().forEach(i -> {
            ItemPedido item = i.toEntity();
            item.setPedido(pedidoACadastrar);
            itensPedido.add(item);
        });

        pedidoACadastrar.setItemPedido(itensPedido);
        pedidoRepositorio.save(pedidoACadastrar);
        System.out.println("ok");
        return PedidoDto.toDto(pedidoACadastrar);
    }

	
	
	public Optional<PedidoDto> atualizarPedido(Long id,PedidoDto pedido){
		if(pedidoRepositorio.existsById(id)) {
			Pedido pedidoEntity = pedido.toEntity();
			pedidoEntity.setId(id);
			pedidoRepositorio.save(pedidoEntity);
			return Optional.of(PedidoDto.toDto(pedidoEntity));
		}
		
		return Optional.empty();
	}
	
	public boolean deletarPedido(Long id) {
		Optional<Pedido> pedido = pedidoRepositorio.findById(id);
		if(pedido.isEmpty()){
			return false;
		}
		pedidoRepositorio.deleteById(id);
		return true;
	}

}
