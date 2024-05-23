package br.org.serratec.api.cel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.serratec.api.cel.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
