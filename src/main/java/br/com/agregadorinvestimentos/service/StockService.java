package br.com.agregadorinvestimentos.service;

import br.com.agregadorinvestimentos.dtos.CreateStockDTO;
import br.com.agregadorinvestimentos.entities.Stock;
import br.com.agregadorinvestimentos.repositories.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO data) {
        var newStock = new Stock(
                data.stockId(),
                data.description()
        );

        stockRepository.save(newStock);
    }

    public void deleteById(String stock_id) {
        var stock  = stockRepository.findById(stock_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        stockRepository.deleteById(stock.getStockId());
    }
}
