package br.com.agregadorinvestimentos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAccountDTO (
        @NotBlank
        String description,
        @NotBlank
        String street,
        @NotNull
        Integer number
) {
}
