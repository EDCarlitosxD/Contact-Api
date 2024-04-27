package com.edcarlitos.conctactapi.exceptions;

public class ContactNotFoundException extends RuntimeException{
    public ContactNotFoundException(String message) {
        super(message);
    }
    public ContactNotFoundException() {
        super("El contacto no existe");
    }
}
