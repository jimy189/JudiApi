package com.br.judiapi.dto.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReuRequestDTO {

    @NotNull(message = "O nome do réu é obrigatório.")
    @NotEmpty(message = "O nome do réu não pode estar vazio.")
    private String nome;
}
