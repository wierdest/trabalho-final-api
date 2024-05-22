package br.org.serratec.api.cel.service;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConverteJSON {
	
	private ObjectMapper mapper = new ObjectMapper();
		
		public <T> T converter(String json, Class<T> classe) {
			try {
				return mapper.readValue(json, classe);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		}

}
