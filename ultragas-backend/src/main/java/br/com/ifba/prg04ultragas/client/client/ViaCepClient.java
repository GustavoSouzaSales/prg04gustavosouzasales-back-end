package br.com.ifba.prg04ultragas.client.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component // Torna a classe gerenciada pelo Spring
public class ViaCepClient {

    private final WebClient webClient;

    public ViaCepClient() {

        // Configura o WebClient apontando para a API do ViaCEP
        this.webClient = WebClient.builder()
                .baseUrl("https://viacep.com.br/ws")
                .build();
    }

    public String buscarCep(String cep) {

        // Faz uma requisição GET para o ViaCEP
        return webClient
                .get()
                .uri("/{cep}/json", cep) // Monta a URL com o CEP informado
                .retrieve() // Executa a requisição
                .bodyToMono(String.class) // Converte a resposta para String
                .block(); // Aguarda o retorno da API
    }
}