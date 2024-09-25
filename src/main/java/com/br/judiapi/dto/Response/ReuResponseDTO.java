package com.br.judiapi.dto.Response;

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
public class ReuResponseDTO {
    @NotNull(message = "O ID do réu é obrigatório.")
    private Long id;

    @NotNull(message = "O nome do réu é obrigatório.")
    @NotEmpty(message = "O nome do réu não pode estar vazio.")
    private String nome;
}
