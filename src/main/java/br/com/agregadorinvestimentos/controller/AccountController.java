package br.com.agregadorinvestimentos.controller;

import br.com.agregadorinvestimentos.dtos.AccountStocksResponseDTO;
import br.com.agregadorinvestimentos.dtos.AssociateAccountDTO;
import br.com.agregadorinvestimentos.infra.config.Descriptions;
import br.com.agregadorinvestimentos.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Associa uma ação a uma conta específica.")
    @PostMapping("/{accountId}/stocks")
    @Transactional
    public ResponseEntity<Void> associateStock (@PathVariable("accountId") String accountId,
                                                @RequestBody AssociateAccountDTO data) {
        System.out.println(accountId);
        accountService.associateStock(accountId, data);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Lista todas as ações de uma conta específica", description = Descriptions.LIST_STOCK_ACCOUNT)
    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStocksResponseDTO>> associateStock (@PathVariable("accountId") String accountId) {
        var stocks = accountService.listStocks(accountId);
        return ResponseEntity.ok(stocks);
    }

}
