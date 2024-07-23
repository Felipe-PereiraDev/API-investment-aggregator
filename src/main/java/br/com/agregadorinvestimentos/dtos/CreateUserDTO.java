package br.com.agregadorinvestimentos.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotBlank
        String username,
        @Email
        String email,
        @NotBlank
        String password
) {
}
