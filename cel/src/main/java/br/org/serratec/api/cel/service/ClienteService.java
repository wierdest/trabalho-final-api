package br.org.serratec.api.cel.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.api.cel.dtos.ClienteDTO;
import br.org.serratec.api.cel.model.Cliente;
import br.org.serratec.api.cel.repository.ClienteRepository;
import jakarta.validation.Valid;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repositorio;
	
	public List<ClienteDTO> obtterTodos() {
        return repositorio.findAll().stream()
                          .map(cliente -> toDto(cliente))
                          .collect(Collectors.toList());
    }

    public ClienteDTO toDto(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getNome_completo(), cliente.getEmail(), cliente.getCpf(), cliente.getTelefone(), cliente.getDataNascimento());
    }


	public List<ClienteDTO> obterTodos() {
		return repositorio.findAll().stream().map(cliente -> toDto(cliente)).toList();
	}

	public ClienteDTO cadastraCliente(ClienteDTO cliente) {
		Cliente clienteEntity = repositorio.save(cliente.toEntity());
		return ClienteDTO.toDto(clienteEntity);
	}
	
	public Optional<ClienteDTO> obterClientePorId(Long id) {
		Optional<Cliente> clienteEntity = repositorio.findById(id);
		if (clienteEntity.isEmpty()) {
			return Optional.of(ClienteDTO.toDto(clienteEntity.get()));
		}
		return Optional.empty();
	}
	
	public Optional<ClienteDTO> atualizarCliente(Long id, @Valid ClienteDTO cliente) {
		if (repositorio.existsById(id)) {
			Cliente clienteEntity = cliente.toEntity();
			clienteEntity.setId(id);
			repositorio.save(clienteEntity);
			return Optional.of(ClienteDTO.toDto(clienteEntity));
		}
		return Optional.empty();
	}
	
	
	public boolean excluirCliente(Long id) {
		Optional<Cliente> cliente = repositorio.findById(id);
		
		if(cliente.isEmpty()){
			return false;
		}
		
		cliente.get();
		repositorio.save(cliente.get());
		repositorio.excluirCliente(id);
		return true;
	}
	
}