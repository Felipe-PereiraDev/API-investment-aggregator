package br.com.agregadorinvestimentos.controller;
import br.com.agregadorinvestimentos.dtos.*;
import br.com.agregadorinvestimentos.entities.User;
import br.com.agregadorinvestimentos.infra.config.Descriptions;
import br.com.agregadorinvestimentos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Cria um novo user", description = "Método que cria um novo usuário")
    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser (@RequestBody @Valid CreateUserDTO dataUser) {
        var user_id = userService.createUser(dataUser);
        return ResponseEntity.created(URI.create("/users/" + user_id.toString())).build();
    }

    @Operation(summary = "Busca um usuário com o ID especificado.", description = Descriptions.LIST_USER_DESC)
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById (@PathVariable("userId") String userId) {
        var user = userService.getUserById(userId);
        return user.map(value -> ResponseEntity.ok(new UserResponseDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listar todos os usuários
    @Operation(summary = "Lista todos os usuários", description = Descriptions.LIST_USERS_DESC)
    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> listUsers () {
        var users = userService.listUsers().stream().map(UserDetailsDTO::new).toList();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Atualizar informações de um usuário", description = Descriptions.PUT_USER)
    @PutMapping("/{userId}")
    @Transactional
    public ResponseEntity<Void> updateUser (@PathVariable("userId") String userId,
                                            @RequestBody UpdateUserDTO data) {
        userService.updateUserById(userId, data);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Deleta um usuário pelo userId")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser (@PathVariable("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    // Método para listar todas as contas do usuário
    @Operation(summary = "Lista todas as contas de um usuário pelo id", description = Descriptions.LIST_ACCOUNT_USER, tags = "Criar Conta")
    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> listAccounts (@PathVariable("userId") String userId) {
        var accounts = userService.listAccounts(userId);
        return ResponseEntity.ok(accounts);
    }

    // Método para cria uma conta
    @Operation (summary = "Cria uma conta para o usuário", description = Descriptions.CREATE_ACCOUNT_USER, tags = "Criar Conta")
    @PostMapping("/{userId}/accounts")
    @Transactional
    public ResponseEntity<Void> createAccount (@PathVariable("userId") String userId,
                                            @RequestBody @Valid CreateAccountDTO data) {
        userService.createAccount(userId, data);
        return ResponseEntity.ok().build();
    }
}
