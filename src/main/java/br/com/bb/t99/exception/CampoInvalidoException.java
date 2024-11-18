package br.com.bb.t99.exception;

public class CampoInvalidoException extends IllegalArgumentException{
    public CampoInvalidoException(String msg) {
        super(msg);
    }
}
