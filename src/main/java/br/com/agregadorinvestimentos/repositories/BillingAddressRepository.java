package br.com.agregadorinvestimentos.repositories;

import br.com.agregadorinvestimentos.entities.Account;
import br.com.agregadorinvestimentos.entities.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
