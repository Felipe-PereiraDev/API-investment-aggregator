package br.com.agregadorinvestimentos.dtos;

public record StockReponseDTO(
        String stockId,
        String description,
        Double price
) {
}
