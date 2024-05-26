package br.org.serratec.api.cel.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import br.org.serratec.api.cel.dtos.ItemPedidoDto;
import br.org.serratec.api.cel.dtos.PedidoDto;
import br.org.serratec.api.cel.dtos.RelatorioPedidoDTO;
import br.org.serratec.api.cel.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	PedidoService servico;
	
	@GetMapping
	@Operation(description="Endpoint para CONSULTAR todos os pedidos do database! Retorna um Pageable de size 2!")
	public ResponseEntity<Page<PedidoDto>> obterTodos(
			@PageableDefault(size=2, page=0, sort="id", direction=Sort.Direction.ASC)
			@Parameter(hidden=true)
			Pageable pageable) {
		return new ResponseEntity<>(servico.obterTodos(pageable), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Operation(description="Endpoint para CONSULTAR um PEDIDO, pesquisando pela ID!")
	public ResponseEntity<PedidoDto> obterPedidoPorId(@PathVariable Long id) {
		Optional<PedidoDto> pedidoDto = servico.obterPedidoPorId(id);
		if(pedidoDto.isPresent()) {
			return new ResponseEntity<>(pedidoDto.get(), HttpStatus.FOUND);
		}		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);	
	}
	
	@GetMapping("/relatorio-pedido/{id}")
	@Operation(description="Endpoint para CONSULTAR um RELATóRIO DO PEDIDO pesquisando pela ID!")

	public ResponseEntity<RelatorioPedidoDTO> obterRelatorioPedido(@PathVariable Long id) {
		Optional<PedidoDto> pedidoDtoOptional= servico.obterPedidoPorId(id);
		if(pedidoDtoOptional.isPresent()) {	
			
			PedidoDto pedidoDto = pedidoDtoOptional.get();
							
			return ResponseEntity.ok(pedidoDto.toRelatorio());
		}
		return ResponseEntity.notFound().build();
	}

//	@PostMapping
//	public ResponseEntity<PedidoDto> cadastrarPedido(@RequestBody @Valid PedidoDto pedido){
//		return new ResponseEntity<PedidoDto>(servico.cadastrarPedido(pedido), HttpStatus.CREATED);
//	}

	@PostMapping
    public ResponseEntity<RelatorioPedidoDTO> cadastrarPedido(@RequestBody @Valid PedidoDto pedido){
        return new ResponseEntity<RelatorioPedidoDTO>(servico.cadastrarPedidoERetornarRelatorio(pedido), HttpStatus.CREATED);
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<PedidoDto> atualizarPedido(@PathVariable Long id, @RequestBody @Valid PedidoDto pedido){

		Optional<PedidoDto> pedidoDto = servico.atualizarPedido(id, pedido);
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
	
	//---------------ControllerItemPedido-----------------------
	@PutMapping("/item-pedido/{id}")
	public ResponseEntity<ItemPedidoDto> atualizarItemPedido(@PathVariable Long id, @RequestBody @Valid ItemPedidoDto itemPedido){

		Optional<ItemPedidoDto> ItemPedidoDto = servico.atualizarItemPedido(id, itemPedido);
		if(ItemPedidoDto.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(ItemPedidoDto.get());
	}
	
	@DeleteMapping("/item-pedido/{id}")
	public ResponseEntity<Void> deletarItemPedido(@PathVariable Long id){
		if(!servico.deletarItemPedido(id)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
	
}
