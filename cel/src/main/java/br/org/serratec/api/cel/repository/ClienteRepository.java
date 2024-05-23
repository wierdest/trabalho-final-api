package br.org.serratec.api.cel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.org.serratec.api.cel.model.Cliente;
import jakarta.transaction.Transactional;



public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	@Modifying
    @Transactional
	@Query("Delete from Cliente l where l.id =:idCliente")
	void excluirCliente(@Param("idCliente") Long idCliente);
}






