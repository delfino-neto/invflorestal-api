package br.com.inv.florestal.api.handler;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public enum AppErrorCodes {
    NO_CODE(0, HttpStatus.NOT_IMPLEMENTED, "Sem código"),
    INCORRECT_PASSWORD(300, HttpStatus.BAD_REQUEST, "Senha incorreta"),
    ACCOUNT_LOCKED(302, HttpStatus.FORBIDDEN, "Usuário está bloqueado"),
    BAD_CREDENTIALS(304, HttpStatus.FORBIDDEN, "Usuário/Senha incorretos"),
    ;

    @Getter
    private final int code;

    @Getter
    private final String description;

    @Getter
    private final HttpStatus httpStatus;

    AppErrorCodes(int code, HttpStatus httpStatus, String description){
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }
}
