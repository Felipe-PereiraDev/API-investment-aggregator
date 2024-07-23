package br.com.agregadorinvestimentos.controller;
import br.com.agregadorinvestimentos.dtos.*;
import br.com.agregadorinvestimentos.entities.User;
import br.com.agregadorinvestimentos.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser (@RequestBody @Valid CreateUserDTO dataUser) {
        var user_id = userService.createUser(dataUser);
        return ResponseEntity.created(URI.create("/users/" + user_id.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById (@PathVariable("userId") String userId) {
        var user = userService.getUserById(userId);
        return user.map(value -> ResponseEntity.ok(new UserResponseDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> listUsers () {
        var users = userService.listUsers().stream().map(UserDetailsDTO::new).toList();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    @Transactional
    public ResponseEntity<Void> updateUser (@PathVariable("userId") String userId,
                                            @RequestBody UpdateUserDTO data) {
        userService.updateUserById(userId, data);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser (@PathVariable("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> listAccounts (@PathVariable("userId") String userId) {
        var accounts = userService.listAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/{userId}/accounts")
    @Transactional
    public ResponseEntity<Void> createAccount (@PathVariable("userId") String userId,
                                            @RequestBody @Valid CreateAccountDTO data) {
        userService.createAccount(userId, data);
        return ResponseEntity.ok().build();
    }
}
