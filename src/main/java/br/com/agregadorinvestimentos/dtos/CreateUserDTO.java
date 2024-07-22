package br.com.agregadorinvestimentos.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotNull
        String username,
        @Email
        String email,
        @NotNull
        String password
) {
}
