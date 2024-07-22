package br.com.agregadorinvestimentos.service;

import br.com.agregadorinvestimentos.client.BrapiClient;
import br.com.agregadorinvestimentos.dtos.CreateStockDTO;
import br.com.agregadorinvestimentos.dtos.PageStocksDTO;
import br.com.agregadorinvestimentos.dtos.StockReponseDTO;
import br.com.agregadorinvestimentos.entities.Stock;
import br.com.agregadorinvestimentos.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StockService {
    @Value("#{environment.TOKEN}")
    private String TOKEN;
    private final StockRepository stockRepository;
    private final BrapiClient brapiClient;

    public StockService(StockRepository stockRepository, BrapiClient brapiClient) {
        this.stockRepository = stockRepository;
        this.brapiClient = brapiClient;
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

    public PageStocksDTO listStocks (int page, int pageSize) {
        var stocks = stockRepository
                .findAll(PageRequest.of(page, pageSize, Sort.Direction.ASC,"stockId"))
                .map(stock -> new StockReponseDTO(
                                stock.getStockId(),
                                stock.getDescription(),
                                getPrice(stock.getStockId())))  ;
        return new PageStocksDTO(
                stocks.getContent(),
                page,
                pageSize,
                stocks.getTotalElements()
        );
    }

    private Double getPrice(String stockId) {
        try {
            var response = brapiClient.getQuote(TOKEN, stockId);

            if (response != null && response.results() != null && !response.results().isEmpty()) {
                return response.results().getFirst().regularMarketPrice();
            } else {
                return 0.0;
            }
        } catch (Exception e) {
            return 0.0;
        }
    }
}
