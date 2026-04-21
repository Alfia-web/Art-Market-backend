package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody com.example.demo.entity.LoginRequest request){
        User user =  repository.findByEmail(request.getEmail());

        if(user == null){
            return ResponseEntity.status(401).body("Пользователь не найден");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(request.getPassword(), user.getPassword())){
            return ResponseEntity.status(401).body("Неверный пароль");
        }

        return ResponseEntity.ok(String.valueOf(user.getId()));
    }
}
