package com.youtube.javaspringmicroservices.dtos;

public record EmailRequestDTO(String to, String subject, String body) {
}
