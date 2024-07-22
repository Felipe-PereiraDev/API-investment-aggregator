package br.com.agregadorinvestimentos.repositories;

import br.com.agregadorinvestimentos.entities.Stock;
import br.com.agregadorinvestimentos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}
