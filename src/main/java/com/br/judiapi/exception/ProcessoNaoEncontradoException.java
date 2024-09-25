package com.br.judiapi.exception;

public class ProcessoNaoEncontradoException extends RuntimeException {
    public ProcessoNaoEncontradoException(String numero) {
        super("Processo não encontrado para o número: " + numero);
    }
}
