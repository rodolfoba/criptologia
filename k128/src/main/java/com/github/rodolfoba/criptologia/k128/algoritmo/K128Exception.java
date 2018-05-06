package com.github.rodolfoba.criptologia.k128.algoritmo;

public class K128Exception extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public K128Exception() {
        super();
    }

    public K128Exception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public K128Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public K128Exception(String message) {
        super(message);
    }

    public K128Exception(Throwable cause) {
        super(cause);
    }
    
}
