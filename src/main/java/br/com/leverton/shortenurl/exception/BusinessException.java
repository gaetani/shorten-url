package br.com.leverton.shortenurl.exception;


import br.com.leverton.shortenurl.exception.domain.CodeError;

public class BusinessException extends RuntimeException {

    private CodeError codeError;
    private String alias;

    public String getAlias() {
        return alias;
    }

    public BusinessException(CodeError codeError, String alias) {
        this.alias = alias;
        this.codeError = codeError;
    }


    public CodeError getCodeError() {
        return codeError;
    }
}
