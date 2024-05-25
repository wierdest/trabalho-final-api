package br.org.serratec.api.cel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.serratec.api.cel.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

    Optional<Categoria> findByNome(String nome);

	
}
