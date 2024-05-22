package br.org.serratec.api.cel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.org.serratec.api.cel.dtos.ViaCEPDTO;

@Service
public class PedidoService {
	
	@Autowired
	ConverteJSON conversorJSON;
	
	
	public ViaCEPDTO conferirCep(String cep) {
		var json = ViaCEPService.obterDados(cep);
		return conversorJSON.converter(json, ViaCEPDTO.class);
	}

}
