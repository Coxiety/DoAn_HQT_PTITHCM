package com.thitracnghiem.hqt.exception;

public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private String errorCode;
    private String errorMessage;
    
    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public BusinessException(ErrorCodes errorCode, String errorMessage) {
        this(errorCode.toString(), errorMessage);
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
