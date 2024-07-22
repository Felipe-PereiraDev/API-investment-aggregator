package br.com.agregadorinvestimentos.dtos;

public record AccountStocksResponseDTO(
        String stockId,
        Integer quantity,
        Double total
) {
}
