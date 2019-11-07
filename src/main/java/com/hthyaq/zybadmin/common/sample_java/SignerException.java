package com.hthyaq.zybadmin.common.sample_java;

public class SignerException extends RuntimeException {
    private static final long serialVersionUID = 1L; 
    
    public SignerException(String message) {
        super(message);
    }
    
    @Override
    public String toString() {
        return "signerException{" + getMessage() + '}';
    }
}
