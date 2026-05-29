package br.com.ifba.prg04ultragas.client.controller;

import br.com.ifba.prg04ultragas.client.client.ViaCepClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Define a classe como controller REST
@RequestMapping("/cep") // Caminho principal da API
@CrossOrigin("*") // Permite acesso pelo frontend
public class ViaCepController {

    @Autowired
    private ViaCepClient viaCepClient;

    @GetMapping("/{cep}") // Busca endereço pelo CEP informado
    public String buscarCep(
            @PathVariable String cep
    ) {

        // Chama o client para consultar o ViaCEP
        return viaCepClient.buscarCep(cep);
    }
}