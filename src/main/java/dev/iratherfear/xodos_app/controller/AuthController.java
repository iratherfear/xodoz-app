package dev.iratherfear.xodos_app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.iratherfear.xodos_app.model.User;
import dev.iratherfear.xodos_app.repository.UserRepository;
import dev.iratherfear.xodos_app.security.JWTTokenUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JWTTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if(!userRepository.findByUsername(user.getUsername()).isEmpty()) {
            throw new RuntimeException("User already exist! try different username");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered succesfully";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }
        String token = jwtTokenUtil.generateToken(user.getUsername());
        return Map.of("token", token);
    }
    
    
}
