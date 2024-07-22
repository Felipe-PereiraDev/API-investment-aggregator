package br.com.agregadorinvestimentos.dtos;

import br.com.agregadorinvestimentos.entities.User;

import java.util.UUID;

public record UserResponseDTO(
        String user_id,
        String username,
        String email
) {
    public UserResponseDTO (User data) {
        this(
                data.getUser_id().toString(),
                data.getUsername(),
                data.getEmail()
        );
    }
}
