package org.aula.controlefinanceiro.exception;

public class SenhaIncorretaException extends RuntimeException {

    public SenhaIncorretaException(String mensagem) {
        super(mensagem);
    }
}