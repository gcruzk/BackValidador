package com.a3.prototipo.Service;

import com.a3.prototipo.Controller.LoginRequest;
import com.a3.prototipo.Controller.LoginResponse;
import com.a3.prototipo.Model.User;
import com.a3.prototipo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user.getEmail());
                return new LoginResponse(token, user.getEmail());
            }
        }
        
        throw new RuntimeException("Credenciais inválidas");
    }
    
    public User register(User user) {
        // ✅ VERIFICAR se email já existe antes de registrar
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}