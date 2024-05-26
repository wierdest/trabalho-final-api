package br.org.serratec.api.cel.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.org.serratec.api.cel.dtos.ClienteDTO;
import br.org.serratec.api.cel.dtos.ViaCEPDTO;
import br.org.serratec.api.cel.model.Cliente;
import br.org.serratec.api.cel.model.Endereco;
import br.org.serratec.api.cel.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ConverteJSON conversorJSON;

	@Autowired
	private ClienteRepository repositorio;
	
	@Autowired
	EmailService emailService;
	
	public Page<ClienteDTO> obterTodos(Pageable pageable) {

		Page<ClienteDTO> clientes = repositorio.findAll(pageable).map(c -> 
			ClienteDTO.toDto(c)
		);
		return clientes;
	}

	public ClienteDTO cadastraOuAcessaCliente(ClienteDTO cliente) {
		Cliente clienteEntity = cliente.toEntity();
		if(cliente.id() == null) {
			
			conferirCPFEmail(cliente);
			
			clienteEntity = criaClienteNovoComEndereco(cliente);
			
			ClienteDTO dto = ClienteDTO.toDto(repositorio.save(clienteEntity));
			
			emailService.enviarEmailTexto(clienteEntity.getEmail(), 
					"Novo usuario cadastrado",
					conversorJSON.converterParaJson(dto)
					);
			
			return ClienteDTO.toDto(repositorio.save(clienteEntity));
			
			
			
		} else {
			
			Optional<Cliente > clienteNoRepo = repositorio.findById(cliente.id());
			if(clienteNoRepo.isPresent()) {
				return ClienteDTO.toDto(clienteEntity);
			} else {
				throw new IllegalArgumentException("Id do cliente é inválida!!");
			}
		}
    }
	
	public Optional<ClienteDTO> atualizarCliente(Long id, ClienteDTO cliente) {
		Optional<Cliente> clienteNoRepositorio = repositorio.findById(id);
		Cliente novoCliente = cliente.toEntity();

		if(clienteNoRepositorio.isPresent()) {
			
			Cliente clienteVelho = clienteNoRepositorio.get();
			if(clienteVelho.getEndereco().getCep() != cliente.endereco().cep()) {
				Optional<ViaCEPDTO> enderecoDTO = obterEndereco(cliente.endereco().cep());
				if(enderecoDTO.isPresent()) {
					Endereco enderecoEntity = enderecoDTO.get().toEntity();
					novoCliente.setEndereco(enderecoEntity);
				}
			}
			novoCliente.setId(clienteVelho.getId());
			repositorio.save(novoCliente);
			return Optional.of(ClienteDTO.toDto(novoCliente));
			
		}
		throw new IllegalArgumentException("Id do cliente é inválida!!");
	}

	private Cliente criaClienteNovoComEndereco(ClienteDTO cliente) {
		 Cliente clienteEntity;
		 clienteEntity = cliente.toEntity();
		 ViaCEPDTO enderecoACadastrar = cliente.endereco();
		 if(enderecoACadastrar == null || enderecoACadastrar.cep() == "") {
		    System.out.println("Endereço nulo ou sem CEP!");
			throw new IllegalArgumentException("Cliente sem CEP! Não é possível cadastrar um cliente sem CEP!");
		 } 
		 else {
			Optional<ViaCEPDTO> enderecoObtido = obterEndereco(enderecoACadastrar.cep());
		    if(enderecoObtido.isPresent()) {
		        Endereco endereco = enderecoObtido.get().toEntity();
		        clienteEntity.setEndereco(endereco);
		    }
		 }
		 
		 return clienteEntity;
	}
	
	private void conferirCPFEmail(ClienteDTO cliente) {
		Optional<Cliente> clientePorCPF = repositorio.findByCpf(cliente.cpf());
		if(clientePorCPF.isPresent()) {
			throw new IllegalArgumentException("CPF já cadastrado! Impossível cadastrar cliente!");
		}
		
		Optional<Cliente> clientePorEmail= repositorio.findByEmail(cliente.email());
		if(clientePorEmail.isPresent()) {
			throw new IllegalArgumentException("EMAIL já cadastrado! Impossível cadastrar cliente!");
		}
	}

	private Optional<ViaCEPDTO> obterEndereco(String cep) {
		var json = ViaCEPService.obterDados(cep);
		ViaCEPDTO dto = conversorJSON.converter(json, ViaCEPDTO.class);
		if(dto == null) {
			// provavelmente erro 500 ou algo de errado com o formato que veio
			return Optional.empty();
		}
		if(dto.cep() == null || dto.bairro() == null) {
			return Optional.empty();
		}
		return Optional.of(dto);
	}
	
	
	public Optional<ClienteDTO> obterClientePorId(Long id) {
		Optional<Cliente> clienteEntity = repositorio.findById(id);
		if (clienteEntity.isEmpty()) {
			return Optional.of(ClienteDTO.toDto(clienteEntity.get()));
		}
		return Optional.empty();
	}
	
	public Cliente obterClientePorIdPedido(Long id) {
        return  repositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

    }
	
	public boolean excluirCliente(Long id) {
		Optional<Cliente> cliente = repositorio.findById(id);
		if(cliente.isEmpty()){
			return false;
		}
			
		repositorio.excluirCliente(id);
		return true;
	}
	
}