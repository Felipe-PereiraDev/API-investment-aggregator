package br.com.agregadorinvestimentos.dtos;

import java.util.List;

public record PageStocksDTO(
        List<StockReponseDTO> listStocks,
        int page,
        int pageSize,
        Long totalElements
){
}
