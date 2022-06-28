package com.example.demo.service;
public class InformationNotFoundException extends RuntimeException {
	
    private static final long serialVersionUID = 2934001112670876945L;
    public InformationNotFoundException(String message) {
        super(message);
    }
}
