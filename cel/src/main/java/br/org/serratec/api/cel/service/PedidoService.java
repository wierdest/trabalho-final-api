package br.org.serratec.api.cel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.api.cel.dtos.ClienteDTO;
import br.org.serratec.api.cel.dtos.ItemPedidoDto;
import br.org.serratec.api.cel.dtos.PedidoDto;
import br.org.serratec.api.cel.dtos.RelatorioPedidoDTO;
import br.org.serratec.api.cel.model.Cliente;
import br.org.serratec.api.cel.model.ItemPedido;
import br.org.serratec.api.cel.model.Pedido;
import br.org.serratec.api.cel.model.Produto;
import br.org.serratec.api.cel.repository.ItemPedidoRepository;
import br.org.serratec.api.cel.repository.PedidoRepository;
import jakarta.validation.Valid;

@Service
public class PedidoService {

	@Autowired
	ConverteJSON conversorJSON;

	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	PedidoRepository pedidoRepositorio;
	
	@Autowired
	ItemPedidoRepository itemRepositorio;
	
	public List<PedidoDto> obterTodos() {
		return pedidoRepositorio.findAll().stream().map(PedidoDto::toDto).toList();
	}

	public Optional<PedidoDto> obterPedidoPorId(Long id) {
		Optional<Pedido> pedidoEntity = pedidoRepositorio.findById(id);
		if (pedidoEntity.isPresent()) {
			return Optional.of(PedidoDto.toDto(pedidoEntity.get()));
		}
		throw new IllegalArgumentException("Id Inválida do Pedido!!");
	}
	
	public RelatorioPedidoDTO cadastrarPedidoERetornarRelatorio(PedidoDto pedido) {
		
		PedidoDto pedidoDto = cadastrarPedido(pedido);
		
		return pedidoDto.toRelatorio();
		
	}
	
	
	public PedidoDto cadastrarPedido(PedidoDto pedido) {	
		
//		ClienteDTO cliente = clienteService.cadastraOuAcessaCliente(pedido.cliente());
		
		Cliente cliente = clienteService.obterClientePorId(pedido.cliente().id());
		
		Pedido pedidoACadastrar = pedido.toEntity();
		pedidoACadastrar.setCliente(cliente);

		List<ItemPedido> itensPedido = new ArrayList<>();
		Double valorTotal = 0.0;
		
		for (ItemPedidoDto i : pedido.itensPedido()) {
			
			Produto produto = produtoService.buscarProdutoPorId(i.produto().id());
			ItemPedido item = i.toEntity();
			item.setProduto(produto);
			
			item.setPedido(pedidoACadastrar);		
			Double valorBruto = item.getPrecoVenda() * item.getQuantidade();
			item.setValorBruto(valorBruto);			
			Double valorDesconto = valorBruto * (item.getPercentualDesconto() / 100);
			Double	valorLiquido = valorBruto - valorDesconto;
			item.setValorLiquido(valorLiquido);
			
			itensPedido.add(item);
			valorTotal += valorLiquido;
		}

		pedidoACadastrar.setItensPedido(itensPedido);
		pedidoACadastrar.setValorTotal(valorTotal);
		
		pedidoRepositorio.save(pedidoACadastrar);
		System.out.println("ok");
		
		return PedidoDto.toDto(pedidoACadastrar);
	
	}

	public Optional<PedidoDto> atualizarPedido(Long id, PedidoDto pedido) {
		
		if (pedidoRepositorio.existsById(id)) {
			
			Optional<Pedido> pedidoNoRepo = pedidoRepositorio.findById(id);			

			Pedido pedidoEntity = pedidoNoRepo.get();
			
			ClienteDTO cliente = clienteService.cadastraOuAcessaCliente(
					ClienteDTO.toDto(pedidoEntity.getCliente())
					);			
	
			pedidoEntity.setCliente(cliente.toEntity());			
			pedidoEntity.setId(id);
			
			pedidoRepositorio.save(pedidoEntity);
			return Optional.of(PedidoDto.toDto(pedidoEntity));
		}
		throw new IllegalArgumentException("Id Inválida do Pedido!!");
} 
			
	

	public boolean deletarPedido(Long id) {
		Optional<Pedido> pedido = pedidoRepositorio.findById(id);
		if (pedido.isEmpty()) {
			return false;
		}
		pedidoRepositorio.deleteById(id);
		return true;
	}
	
	//---------------ServiceItemPedido-----------------------
	
	public Optional<ItemPedidoDto> atualizarItemPedido(Long id, @Valid ItemPedidoDto itemPedido){
		if(itemRepositorio.existsById(id)) {
			
			ItemPedido item = itemRepositorio.findById(id).orElseThrow();
			
			item.setQuantidade(itemPedido.quantidade());
			item.setPrecoVenda(itemPedido.precoVenda());
			item.setPercentualDesconto(itemPedido.percentualDesconto());
			
			Double valorBruto = item.getQuantidade() * item.getPrecoVenda();
			item.setValorBruto(valorBruto);
			
			Double valorDesconto = valorBruto * (item.getPercentualDesconto() / 100);
			Double valorLiquido = valorBruto - valorDesconto;
			item.setValorLiquido(valorLiquido);
			
			itemRepositorio.save(item);
			
			Pedido pedido = item.getPedido();
			Double valorTotal = pedido.getItensPedido()
					.stream()
					.mapToDouble(ItemPedido::getValorLiquido).sum();
			
			pedido.setValorTotal(valorTotal);			
			return Optional.of(ItemPedidoDto.toDto(item));	
			
		}
		return Optional.empty();
	}
		
	
	public boolean deletarItemPedido(Long id) {
		Optional<ItemPedido> ItemPedido = itemRepositorio.findById(id);
		if (ItemPedido.isEmpty()) {
			return false;
		}
		itemRepositorio.deleteById(id);
		return true;
	}
}
