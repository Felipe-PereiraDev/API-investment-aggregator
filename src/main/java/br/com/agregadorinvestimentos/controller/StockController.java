package br.com.agregadorinvestimentos.controller;

import br.com.agregadorinvestimentos.dtos.CreateStockDTO;
import br.com.agregadorinvestimentos.dtos.PageStocksDTO;
import br.com.agregadorinvestimentos.infra.config.Descriptions;
import br.com.agregadorinvestimentos.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @Operation(summary = "Lista todos os stocks", description = Descriptions.LIST_STOCKS)
    @GetMapping
    public ResponseEntity<PageStocksDTO> listStocks (@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(stockService.listStocks(page, pageSize));

    }

    @Operation(summary = "Cria um novo Stock")
    @PostMapping
    @Transactional
    public ResponseEntity<Void> createStock (@RequestBody @Valid CreateStockDTO data) {
        stockService.createStock(data);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deleta um stock pelo stockId")
    @DeleteMapping("/{stockId}")
    public ResponseEntity<Void> deleteById (@PathVariable("stockId") String stockId) {
       stockService.deleteById(stockId);
       return ResponseEntity.noContent().build();
    }
}
