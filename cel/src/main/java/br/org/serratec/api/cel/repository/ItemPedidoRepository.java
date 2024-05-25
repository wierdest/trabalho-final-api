package br.org.serratec.api.cel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.serratec.api.cel.dtos.ItemPedidoDto;
import br.org.serratec.api.cel.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

}
