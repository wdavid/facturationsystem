package com.facturation.system.facturationsystem.services;

import com.facturation.system.facturationsystem.models.UserModel;
import com.facturation.system.facturationsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Crear o actualizar usuario (encriptando la contraseña)
    public UserModel saveUser(UserModel user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está registrado");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encriptación
        return userRepository.save(user);
    }

    // Obtener todos los usuarios (¡Cuidado en producción! No exponer contraseñas)
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    // Buscar por ID
    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Buscar por username
    public Optional<UserModel> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Eliminar usuario
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
