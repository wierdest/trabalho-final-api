package br.org.serratec.api.cel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.api.cel.dtos.ClienteDTO;
import br.org.serratec.api.cel.dtos.ItemPedidoDto;
import br.org.serratec.api.cel.dtos.PedidoDto;
import br.org.serratec.api.cel.model.ItemPedido;
import br.org.serratec.api.cel.model.Pedido;
import br.org.serratec.api.cel.repository.ClienteRepository;
import br.org.serratec.api.cel.repository.PedidoRepository;


@Service
public class PedidoService {
	
	@Autowired
	ConverteJSON conversorJSON;
	
	@Autowired
	PedidoRepository pedidoRepositorio;
	
	@Autowired
	ClienteService clienteService;
	
	public List<PedidoDto> obterTodos() {
		return pedidoRepositorio.findAll().stream().map(p -> PedidoDto.toDto(p)).toList();
	}

	public Optional<PedidoDto> obterPedidoPorId(Long id){
		Optional<Pedido> pedidoEntity = pedidoRepositorio.findById(id);
		if(pedidoEntity.isPresent()) {
			return Optional.of(PedidoDto.toDto(pedidoEntity.get()));
		}
		return Optional.empty();
	}

	public PedidoDto cadastrarPedido(PedidoDto pedido) {	
		
		ClienteDTO cliente = clienteService.cadastraOuAcessaCliente(pedido.cliente());
		
        Pedido pedidoACadastrar = pedido.toEntity();
        
        pedidoACadastrar.setCliente(cliente.toEntity());
        
        List<ItemPedido> itensPedido = new ArrayList<>();
        Double valorTotalPedido = 0.0;
        
        for(ItemPedidoDto i : pedido.itensPedido()) {
        	ItemPedido item = i.toEntity();
        	// fazer = conferir o desconto
        	valorTotalPedido += item.getValorLiquido() * item.getQuantidade();
        	item.setPedido(pedidoACadastrar);
        	itensPedido.add(item);
        }
        
        pedidoACadastrar.setValorTotal(valorTotalPedido);
        pedidoACadastrar.setItensPedido(itensPedido);
		pedidoRepositorio.save(pedidoACadastrar);
		System.out.println("ok");
		
		return PedidoDto.toDto(pedidoACadastrar);
	
	}
	
	public Optional<PedidoDto> atualizarPedido(Long id,PedidoDto pedido){
		if(pedidoRepositorio.existsById(id)) {
			Pedido pedidoEntity = pedido.toEntity();
			
			ClienteDTO cliente = clienteService.cadastraOuAcessaCliente(pedido.cliente());
			
			pedidoEntity.setCliente(cliente.toEntity());
			
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
