package com.br.judiapi;

import com.br.judiapi.dto.Request.ProcessoRequestDTO;
import com.br.judiapi.dto.Request.ReuRequestDTO;
import com.br.judiapi.dto.Response.ProcessoResponseDTO;
import com.br.judiapi.dto.Response.ReuResponseDTO;
import com.br.judiapi.service.ProcessoService;
import com.br.judiapi.web.ProcessoController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ProcessoController.class) // Foca no teste do Controller
public class ProcessoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessoService processoService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProcessoRequestDTO processoRequestDTO;
    private ProcessoResponseDTO processoResponseDTO;

    @BeforeEach
    void setUp() {
        // Inicializa um processo de exemplo
        processoRequestDTO = new ProcessoRequestDTO("12345678901234567890", Arrays.asList(
                new ReuRequestDTO("Réu 1"),
                new ReuRequestDTO("Réu 2")
        ));

        processoResponseDTO = new ProcessoResponseDTO(1L, "12345678901234567890", Arrays.asList(
                new ReuResponseDTO(1L, "Réu 1"),
                new ReuResponseDTO(2L, "Réu 2")
        ));
    }

    @Test
    void testCriarProcesso() throws Exception {
        // Mockando o comportamento do service
        Mockito.when(processoService.criarProcesso(Mockito.any(ProcessoRequestDTO.class)))
                .thenReturn(processoResponseDTO);

        mockMvc.perform(post("/processos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(processoRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.numero").value("12345678901234567890"))
                .andExpect(jsonPath("$.reus[0].nome").value("Réu 1"))
                .andExpect(jsonPath("$.reus[1].nome").value("Réu 2"));
    }

    @Test
    void testListarProcessos() throws Exception {
        List<ProcessoResponseDTO> processos = Arrays.asList(processoResponseDTO);

        // Mockando o comportamento do service
        Mockito.when(processoService.listarProcessos()).thenReturn(processos);

        mockMvc.perform(get("/processos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].numero").value("12345678901234567890"))
                .andExpect(jsonPath("$[0].reus[0].nome").value("Réu 1"))
                .andExpect(jsonPath("$[0].reus[1].nome").value("Réu 2"));
    }

    @Test
    void testExcluirProcesso() throws Exception {
        Mockito.doNothing().when(processoService).excluirProcessoPorNumero("12345678901234567890");

        mockMvc.perform(delete("/processos/numero/12345678901234567890")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAdicionarReu() throws Exception {
        ReuRequestDTO reuRequestDTO = new ReuRequestDTO("Réu 3");

        // Mockando o comportamento do service
        Mockito.doNothing().when(processoService).adicionarReu(1L, reuRequestDTO);

        mockMvc.perform(post("/processos/1/reus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reuRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Réu adicionado com sucesso!"));
    }
}
