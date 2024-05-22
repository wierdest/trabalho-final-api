package br.org.serratec.api.cel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.serratec.api.cel.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
