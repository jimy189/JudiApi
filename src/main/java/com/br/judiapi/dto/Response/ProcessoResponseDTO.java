package com.br.judiapi.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoResponseDTO {
    private Long id;
    private String numero;
    private List<ReuResponseDTO> reus;
}
