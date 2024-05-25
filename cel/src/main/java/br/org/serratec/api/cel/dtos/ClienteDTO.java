package br.org.serratec.api.cel.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.org.serratec.api.cel.config.Mapper;
import br.org.serratec.api.cel.model.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;


@JsonIgnoreProperties(ignoreUnknown=true)
public record ClienteDTO(
        Long id,
        @NotBlank(message = "Digite o nome completo.")
        String nome, 
        @NotBlank
        @Email(message =  "Informe um e-mail válido com @.")
        String email, 
        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "Digite um CPF válido de 11 dígitos.")
        String cpf, 
        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "O telefone não pode estar em branco.")
        String telefone,
        @NotNull(message = "O endereço não pode estar em branco.")
        ViaCEPDTO endereco,
        @NotNull
        @Past(message = "Data de nascimento não pode ser maior que a data atual.")
        LocalDate dataNascimento

        ) {

    public Cliente toEntity() {

        return Mapper.getMapper().convertValue(this, Cliente.class);
    }

    public static ClienteDTO toDto(Cliente clienteEntity) {

        return Mapper.getMapper().convertValue(clienteEntity, ClienteDTO.class);
    }
}

