package com.edcarlitos.conctactapi.exceptions;

public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist(String message) {
        super(message);
    }
    public UserAlreadyExist(){
        super("El usuario ya existe");
    }
}
