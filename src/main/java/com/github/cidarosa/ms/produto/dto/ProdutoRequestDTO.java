package com.github.cidarosa.ms.produto.dto;

import com.github.cidarosa.ms.produto.entities.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProdutoRequestDTO {

    private Long id;

    @NotBlank(message = "O campo nome é requerido")
    @Size(min = 3, max = 100, message = "O campo nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O campo descrição é requerido")
    @Size(min = 10, message = "A descrição deve ter no mínimo 10 caracteres")
    private String descricao;

    @NotNull(message = "O campo valor é requerido")
    @Positive(message = "O valor deve ser um número positivo e maior que zero")
    private Double valor;

    @NotNull(message = "Campo categoria é requerido")
    private Long categoriaId;

    public ProdutoRequestDTO(Produto produto) {
       id = produto.getId();
       nome = produto.getNome();
       descricao = produto.getDescricao();
       valor = produto.getValor();
       categoriaId = produto.getCategoria().getId();
    }
}
