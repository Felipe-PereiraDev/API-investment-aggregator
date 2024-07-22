package br.com.agregadorinvestimentos.service;

import br.com.agregadorinvestimentos.dtos.AccountResponseDTO;
import br.com.agregadorinvestimentos.dtos.CreateAccountDTO;
import br.com.agregadorinvestimentos.dtos.CreateUserDTO;
import br.com.agregadorinvestimentos.dtos.UpdateUserDTO;
import br.com.agregadorinvestimentos.entities.Account;
import br.com.agregadorinvestimentos.entities.BillingAddress;
import br.com.agregadorinvestimentos.entities.User;
import br.com.agregadorinvestimentos.repositories.AccountRepository;
import br.com.agregadorinvestimentos.repositories.BillingAddressRepository;
import br.com.agregadorinvestimentos.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository,
                       AccountRepository accountRepository,
                       BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser (CreateUserDTO data) {
        // Conversão de DTO para user
        var user = new User(
                UUID.randomUUID(),
                data.username(),
                data.email(),
                data.password(),
                Instant.now(),
                null);
        var userSaved = userRepository.save(user);
        return userSaved.getUser_id();
    }

    public Optional<User> getUserById (String user_id) {
        return userRepository.findById(UUID.fromString(user_id));
    }

    public List<User> listUsers () {
        return userRepository.findAll();
    }

    public void updateUserById (String user_id, UpdateUserDTO data) {
        var id = UUID.fromString(user_id);
        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();
            user.updateInfo(data);
            userRepository.save(user);
        }
    }

    public void deleteById (String user_id) {
        var id = UUID.fromString(user_id);
        var userExists = userRepository.existsById(id);
        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    @Transactional
    public void createAccount(String userId, CreateAccountDTO data) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var account = new Account(
                null,
                user,
                data.description(),
                null,
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                account,
                data.street(),
                data.number()
        );


        billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDTO> listAccounts(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
       return user.getAccounts()
                .stream()
                .map(ac -> new AccountResponseDTO(ac.getAccountId().toString(), ac.getDescription()))
                .toList();
    }
}
