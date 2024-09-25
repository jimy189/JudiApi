package com.br.judiapi;
import com.br.judiapi.dto.Request.ProcessoRequestDTO;
import com.br.judiapi.dto.Request.ReuRequestDTO;
import com.br.judiapi.dto.Response.ProcessoResponseDTO;
import com.br.judiapi.entity.Processo;
import com.br.judiapi.entity.Reu;
import com.br.judiapi.exception.BadRequestExceptionCustom;
import com.br.judiapi.exception.NotFoundCustom;
import com.br.judiapi.repository.ProcessoRepository;
import com.br.judiapi.service.ProcessoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
class ProcessoServiceTest {

    @Autowired
    private ProcessoService processoService;

    @MockBean
    private ProcessoRepository processoRepository;

    private ProcessoRequestDTO processoRequestDTO;
    private Processo processo;
    private ReuRequestDTO reuRequestDTO;

    @BeforeEach
    void setUp() {
        // Inicializando DTO e Entidade de exemplo
        processoRequestDTO = new ProcessoRequestDTO("12345678901234567890", Arrays.asList(
                new ReuRequestDTO("Réu 1"),
                new ReuRequestDTO("Réu 2")
        ));

        processo = new Processo();
        processo.setId(1L);
        processo.setNumero("12345678901234567890");
        processo.setReus(Arrays.asList(new Reu(1L, "Réu 1", processo), new Reu(2L, "Réu 2", processo)));

        reuRequestDTO = new ReuRequestDTO("Réu 3");
    }

    @Test
    void criarProcesso_Sucesso() {
        // Mockando comportamento do repositório
        when(processoRepository.existsByNumero(anyString())).thenReturn(false);
        when(processoRepository.save(any(Processo.class))).thenReturn(processo);

        ProcessoResponseDTO response = processoService.criarProcesso(processoRequestDTO);

        assertNotNull(response);
        assertEquals("12345678901234567890", response.getNumero());
        assertEquals(2, response.getReus().size());
    }

    @Test
    void criarProcesso_NumeroExistente() {
        // Mockando situação onde o número já existe
        when(processoRepository.existsByNumero(anyString())).thenReturn(true);

        BadRequestExceptionCustom exception = assertThrows(BadRequestExceptionCustom.class, () -> {
            processoService.criarProcesso(processoRequestDTO);
        });

        assertEquals("O número de processo já existe.", exception.getMessage());
    }

    @Test
    void listarProcessos_Sucesso() {
        // Mockando retorno do repositório
        when(processoRepository.findAll()).thenReturn(Collections.singletonList(processo));

        List<ProcessoResponseDTO> response = processoService.listarProcessos();

        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
        assertEquals("12345678901234567890", response.get(0).getNumero());
        assertEquals(2, response.get(0).getReus().size());
    }

    @Test
    void excluirProcessoPorNumero_Sucesso() {
        // Mockando processo existente no banco
        when(processoRepository.findByNumero(anyString())).thenReturn(processo);

        processoService.excluirProcessoPorNumero("12345678901234567890");

        // Verificar se o método de deletar foi chamado
        verify(processoRepository, times(1)).delete(processo);
    }

    @Test
    void excluirProcessoPorNumero_ProcessoNaoEncontrado() {
        // Mockando cenário de processo não encontrado
        when(processoRepository.findByNumero(anyString())).thenReturn(null);

        NotFoundCustom exception = assertThrows(NotFoundCustom.class, () -> {
            processoService.excluirProcessoPorNumero("12345678901234567890");
        });

        assertEquals("Processo não encontrado para o número: 12345678901234567890", exception.getMessage());
    }

    @Test
    void adicionarReu_ProcessoNaoEncontrado() {
        // Mockando processo não encontrado
        when(processoRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundCustom exception = assertThrows(NotFoundCustom.class, () -> {
            processoService.adicionarReu(1L, reuRequestDTO);
        });

        assertEquals("Processo não encontrado.", exception.getMessage());
    }
}
