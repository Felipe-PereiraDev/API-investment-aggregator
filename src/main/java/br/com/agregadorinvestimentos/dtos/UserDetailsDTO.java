package br.com.agregadorinvestimentos.dtos;

import br.com.agregadorinvestimentos.entities.User;

import java.time.Instant;

public record UserDetailsDTO(
        String user_id,
        String username,
        String email,
        Instant creationTimestamp
) {
    public UserDetailsDTO(User dataUser) {
        this(
                dataUser.getUser_id().toString(),
                dataUser.getUsername(),
                dataUser.getEmail(),
                dataUser.getCreationTimestamp()
        );
    }
}
