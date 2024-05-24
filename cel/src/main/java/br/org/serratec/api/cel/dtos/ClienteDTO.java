package br.org.serratec.api.cel.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.org.serratec.api.cel.config.Mapper;
import br.org.serratec.api.cel.model.Cliente;

@JsonIgnoreProperties(ignoreUnknown=true)
public record ClienteDTO(
        Long id,
        String nome_completo, 
        String email, 
        String cpf, 
        String telefone,
        ViaCEPDTO endereco,
        LocalDate dataNascimento

        ) {

    public Cliente toEntity() {

        return Mapper.getMapper().convertValue(this, Cliente.class);
    }

    public static ClienteDTO toDto(Cliente clienteEntity) {

        return Mapper.getMapper().convertValue(clienteEntity, ClienteDTO.class);
    }
}

