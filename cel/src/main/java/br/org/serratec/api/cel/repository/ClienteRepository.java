package br.org.serratec.api.cel.repository;

import java.util.List;
import java.util.Optional;

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
	
	Optional<Cliente> findByCpf(String cpf);
	
	Optional<Cliente> findByEmail(String email);
	
	@Query(value="""
		    SELECT  
		        e.id,
		        e.cep,
		        e.logradouro,
		        e.complemento,
		        e.bairro,
		        e.localidade,
		        e.uf
		    FROM clientes c 
		    JOIN enderecos e ON c.endereco_id = e.id
		    WHERE e.cep = :cep
		    GROUP BY 
		    	e.id,
		    	e.cep,
		        e.logradouro,
		        e.complemento,
		        e.bairro,
		        e.localidade,
		        e.uf""", nativeQuery=true)
	
	List<Object[]> findByCep(@Param("cep") String cep);
	
	@Query(value="""
		    SELECT  
		        e
		    FROM clientes c 
		    JOIN enderecos e ON c.endereco_id = e.id
		    GROUP BY e""", nativeQuery=true)
	List<Object[]> findAllEndereco();

}






