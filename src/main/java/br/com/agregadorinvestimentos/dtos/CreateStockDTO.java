package br.com.agregadorinvestimentos.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateStockDTO (
        @NotBlank
        String stockId,
        @NotBlank
        String description
){
}
