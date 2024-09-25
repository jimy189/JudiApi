package com.br.judiapi.service;

import com.br.judiapi.dto.Request.ProcessoRequestDTO;
import com.br.judiapi.dto.Request.ReuRequestDTO;
import com.br.judiapi.dto.Response.ProcessoResponseDTO;
import com.br.judiapi.dto.Response.ReuResponseDTO;
import com.br.judiapi.entity.Processo;
import com.br.judiapi.entity.Reu;
import com.br.judiapi.exception.BadRequestExceptionCustom;
import com.br.judiapi.exception.NotFoundCustom;
import com.br.judiapi.repository.ProcessoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessoService {


    private final ProcessoRepository processoRepository;

    public ProcessoResponseDTO criarProcesso(ProcessoRequestDTO processoDTO)  {

        // Verifica se o processo já existe antes de salvar (baseado no número)
        if (processoRepository.existsByNumero(processoDTO.getNumero())) {
            throw new BadRequestExceptionCustom("O número de processo já existe.");
        }

        Processo processo = new Processo();
        processo.setNumero(processoDTO.getNumero());

        List<Reu> reus = processoDTO.getReus().stream().map(reuDTO -> {
            Reu reu = new Reu();
            reu.setNome(reuDTO.getNome());
            reu.setProcesso(processo);
            return reu;
        }).collect(Collectors.toList());

        processo.setReus(reus);
        processoRepository.save(processo);

        // Converter o processo salvo em um Response DTO
        List<ReuResponseDTO> reusResponse = reus.stream()
                .map(reu -> new ReuResponseDTO(reu.getId(), reu.getNome()))
                .collect(Collectors.toList());

        return new ProcessoResponseDTO(processo.getId(), processo.getNumero(), reusResponse);
    }

    public List<ProcessoResponseDTO> listarProcessos() {
        List<Processo> processos = processoRepository.findAll();

        // Converter a lista de Processo para ProcessoResponseDTO
        return processos.stream().map(processo -> {
            List<ReuResponseDTO> reusResponse = processo.getReus().stream()
                    .map(reu -> new ReuResponseDTO(reu.getId(), reu.getNome()))
                    .collect(Collectors.toList());

            return new ProcessoResponseDTO(processo.getId(), processo.getNumero(), reusResponse);
        }).collect(Collectors.toList());
    }

    public void excluirProcessoPorNumero(String numero) {
        Processo processo = processoRepository.findByNumero(numero);
        if (processo == null) {
            throw new NotFoundCustom("Processo não encontrado para o número: " + numero);
        }

        processoRepository.delete(processo);
    }

    public void adicionarReu(Long processoId, ReuRequestDTO reuDTO) {
        // Buscar processo pelo ID
        Processo processo = processoRepository.findById(processoId)
                .orElseThrow(() -> new NotFoundCustom("Processo não encontrado."));

        // Criar um novo réu e associar ao processo
        Reu reu = new Reu();
        reu.setNome(reuDTO.getNome());
        reu.setProcesso(processo); // Associando o réu ao processo

        // Adicionar o réu ao processo
        processo.getReus().add(reu);
        processoRepository.save(processo);
    }
}
