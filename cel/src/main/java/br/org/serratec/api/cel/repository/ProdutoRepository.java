package br.org.serratec.api.cel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.serratec.api.cel.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
