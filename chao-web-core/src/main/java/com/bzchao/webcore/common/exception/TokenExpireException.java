package com.bzchao.webcore.common.exception;

public class TokenExpireException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TokenExpireException(String message) {
        super(message);
    }

    public TokenExpireException(Throwable cause) {
        super(cause);
    }

    public TokenExpireException(String message, Throwable cause) {
        super(message, cause);
    }
}
