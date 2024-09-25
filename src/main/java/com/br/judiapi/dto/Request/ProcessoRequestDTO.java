package com.br.judiapi.dto.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoRequestDTO {
    @NotNull(message = "O número do processo é obrigatório.")
    @NotEmpty(message = "O número do processo não pode estar vazio.")
    private String numero;

    @NotNull(message = "A lista de réus é obrigatória.")
    @Valid
    private List<@Valid ReuRequestDTO> reus;
}
