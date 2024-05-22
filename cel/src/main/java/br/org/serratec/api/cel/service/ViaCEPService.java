package br.org.serratec.api.cel.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ViaCEPService {
	
	private final static String baseURL = "https://viacep.com.br/ws/";
	private final static String formatoJSON = "/json/";
	
	private static String buscaDados(String endereco) {
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(endereco)).build();

		HttpResponse<String> response = null;

		try {
			response = client.send(request, BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return response.body();
	}
	
	public static String obterDados(String cep) {
		String endereco = baseURL + cep + formatoJSON;
		return buscaDados(endereco);
	}
	

}
