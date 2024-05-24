package br.org.serratec.api.cel.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.api.cel.dtos.ClienteDTO;
import br.org.serratec.api.cel.dtos.ViaCEPDTO;
import br.org.serratec.api.cel.model.Cliente;
import br.org.serratec.api.cel.model.Endereco;
import br.org.serratec.api.cel.repository.ClienteRepository;
import jakarta.validation.Valid;

@Service
public class ClienteService {

	@Autowired
	ConverteJSON conversorJSON;

	@Autowired
	private ClienteRepository repositorio;
	
//	public List<ClienteDTO> obterTodos() {
//        return repositorio.findAll().stream()
//                          .map(cliente -> ClienteDTO.toDto(cliente))
//                          .collect(Collectors.toList());
//    }

	public List<ClienteDTO> obterTodos() {
		
		return repositorio.findAll().stream().map(cliente -> ClienteDTO.toDto(cliente)).toList();
	}

	public ClienteDTO cadastraCliente(ClienteDTO cliente) {

        ViaCEPDTO enderecoACadastrar = cliente.endereco();
        if(enderecoACadastrar == null || enderecoACadastrar.cep() == "") {
            System.out.println("Endereço nulo ou sem CEP!");
            return null;
        } 

        Cliente clienteEntity = cliente.toEntity();

        Optional<ViaCEPDTO> enderecoObtido = obterEndereco(enderecoACadastrar.cep());
        if(enderecoObtido.isPresent()) {
            System.out.println("Encontrou endereço!");
            Endereco endereco = enderecoObtido.get().toEntity();

            clienteEntity.setEndereco(endereco);
        }

        return ClienteDTO.toDto(clienteEntity);
    }
	
	public Optional<ViaCEPDTO> obterEndereco(String cep) {
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
	
	public Optional<ClienteDTO> atualizarCliente(Long id, @Valid ClienteDTO cliente) {
		
		Optional<Cliente> clienteNoRepositorio = repositorio.findById(id);
		Cliente novoCliente = cliente.toEntity();

		if(clienteNoRepositorio.isPresent()) {
			Cliente clienteVelho = clienteNoRepositorio.get();
			if(clienteVelho.getEndereco().getCep() != cliente.endereco().cep()) {
				// cliente com o cep diferente, precisa atualizar o endereço
				// obtem novo endereço
				// validar o cep, claro, quando fizermos a parte de validação
				Optional<ViaCEPDTO> enderecoDTO = obterEndereco(cliente.endereco().cep());
				// fazer = olhar os requisitos de validação do endereço
				if(enderecoDTO.isPresent()) {
					Endereco enderecoEntity = enderecoDTO.get().toEntity();
					novoCliente.setEndereco(enderecoEntity);
				}
			}
			novoCliente.setId(id);
			repositorio.save(novoCliente);
			return Optional.of(ClienteDTO.toDto(novoCliente));
			
		}
		return Optional.empty();
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