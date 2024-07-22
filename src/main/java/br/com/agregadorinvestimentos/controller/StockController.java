package br.com.agregadorinvestimentos.controller;

import br.com.agregadorinvestimentos.dtos.CreateStockDTO;
import br.com.agregadorinvestimentos.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Void> createStock (@RequestBody CreateStockDTO data) {
        stockService.createStock(data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{stockId}")
    public ResponseEntity<Void> deleteById (@PathVariable("stockId") String stockId) {
       stockService.deleteById(stockId);
       return ResponseEntity.noContent().build();
    }
}
