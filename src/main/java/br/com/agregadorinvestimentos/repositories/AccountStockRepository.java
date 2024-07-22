package br.com.agregadorinvestimentos.repositories;

import br.com.agregadorinvestimentos.entities.Account;
import br.com.agregadorinvestimentos.entities.AccountStock;
import br.com.agregadorinvestimentos.entities.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
