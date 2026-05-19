package controller;

import com.example.demo.entity.User;
import exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email уже существует");
        }
        userRepository.save(user);
        return ResponseEntity.ok("OK" + user.getId());
    }

    @PostMapping("/login")
    public String login(@RequestBody User request) {
        String email = request.getEmail().trim().toLowerCase();
        User user = userRepository.findByEmail(email);

        if (user != null && request.getPassword().equals(user.getPassword())) {
            return "OK" + user.getId() + "|" + user.getEmail();
        }
        return "Ошибка входа, проверьте пароль и логин";
    }

    @GetMapping("/{userId}/balance")
    public BigDecimal getBalance(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        return user.getBalance() != null ? user.getBalance() : BigDecimal.ZERO;
    }

    @PostMapping("/{userId}/addMoney")
    public BigDecimal addMoney(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));

        BigDecimal current = user.getBalance() != null ? user.getBalance() : BigDecimal.ZERO;

        user.setBalance(current.add(amount));
        userRepository.save(user);

        return user.getBalance();
    }
}