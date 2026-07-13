package br.com.ifba.prg04ultragas.endereco.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoRequestDTO {

    // Nome para identificar o endereço
    @NotBlank(message = "O título não pode ser vazio")
    private String titulo;

    @NotBlank(message = "O CEP não pode ser vazio")
    private String cep;

    @NotBlank(message = "O endereço não pode ser vazio")
    private String endereco;

    @NotBlank(message = "O número não pode ser vazio")
    private String numero;

    @NotBlank(message = "O bairro não pode ser vazio")
    private String bairro;

    @NotBlank(message = "A cidade não pode ser vazia")
    private String cidade;

    @NotBlank(message = "A UF não pode ser vazia")
    private String uf;

    // Indica se é o endereço principal
    private Boolean principal;

    // Usuário dono do endereço
    @NotNull(message = "O usuário é obrigatório")
    private Long usuarioId;
}