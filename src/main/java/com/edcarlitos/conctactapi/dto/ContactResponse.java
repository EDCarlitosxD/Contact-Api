package com.edcarlitos.conctactapi.dto;

public record ContactResponse(Long id, String fullName, String phoneNumber, Long userId) {
}
