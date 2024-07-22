package br.com.agregadorinvestimentos.service;

import br.com.agregadorinvestimentos.client.BrapiClient;
import br.com.agregadorinvestimentos.dtos.AccountStocksResponseDTO;
import br.com.agregadorinvestimentos.dtos.AssociateAccountDTO;
import br.com.agregadorinvestimentos.entities.AccountStock;
import br.com.agregadorinvestimentos.entities.AccountStockId;
import br.com.agregadorinvestimentos.repositories.AccountRepository;
import br.com.agregadorinvestimentos.repositories.AccountStockRepository;
import br.com.agregadorinvestimentos.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Value("#{environment.TOKEN}")
    private String TOKEN;
    private final StockRepository stockRepository;
    private final AccountRepository accountRepository;
    private final AccountStockRepository accountStockRepository;
    private final BrapiClient brapiClient;
    public AccountService(StockRepository stockRepository,
                          AccountRepository accountRepository,
                          AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.stockRepository = stockRepository;
        this.accountRepository = accountRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }


    public void associateStock(String accountId, AssociateAccountDTO data) {
        // verificando se existe uma account com esse id no banco de dados
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        // verificando se existe um stock com esse id no banco de dados
        var stock = stockRepository.findById(data.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStockId());

        var entity = new AccountStock(
                id,
                account,
                stock,
                data.quantity()
        );
        accountStockRepository.save(entity);
    }

    public List<AccountStocksResponseDTO> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND)));
        return account.getAccountStocks()
                .stream()
                .map(as -> new AccountStocksResponseDTO(
                        as.getStock().getStockId(),
                        as.getQuantity(),
                        getTotal(as.getQuantity(), as.getStock().getStockId())))
                .toList();
    }

    private Double getTotal(Integer quantity, String stockId) {
        try {
            var response = brapiClient.getQuote(TOKEN, stockId);

            if (response != null && response.results() != null && !response.results().isEmpty()) {
                var price = response.results().getFirst().regularMarketPrice();
                return quantity * price;
            } else {
                return 0.0;
            }
        } catch (Exception e) {
            return 0.0;
        }
    }

}
