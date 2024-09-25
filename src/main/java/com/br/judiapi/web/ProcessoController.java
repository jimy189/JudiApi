package com.br.judiapi.web;

import com.br.judiapi.dto.Request.ProcessoRequestDTO;
import com.br.judiapi.dto.Request.ReuRequestDTO;
import com.br.judiapi.dto.Response.ProcessoResponseDTO;
import com.br.judiapi.service.ProcessoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/processos")
@RequiredArgsConstructor
public class ProcessoController {

    private final ProcessoService processoService;

    @PostMapping
    public ResponseEntity<?> criarProcesso(@Valid @RequestBody ProcessoRequestDTO processoDTO)  {
        ProcessoResponseDTO responseDTO = processoService.criarProcesso(processoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProcessoResponseDTO>> listarProcessos() {
        List<ProcessoResponseDTO> processos = processoService.listarProcessos();
        return ResponseEntity.ok(processos);
    }

    @DeleteMapping("/numero/{numero}")
    public ResponseEntity<String> excluirProcesso(@PathVariable String numero) {
        processoService.excluirProcessoPorNumero(numero);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reus")
    public ResponseEntity<String> adicionarReu(@PathVariable Long id, @Valid @RequestBody ReuRequestDTO reuDTO) {
        processoService.adicionarReu(id, reuDTO);
        return ResponseEntity.ok("Réu adicionado com sucesso!");
    }
}
