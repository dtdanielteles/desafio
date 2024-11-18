package br.com.bb.t99.exception;

public class NaoAutorizadoException extends RuntimeException{
    public NaoAutorizadoException(String msg) {
        super(msg);
    }
}
