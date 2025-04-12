package com.facturation.system.facturationsystem.controllers;

import com.facturation.system.facturationsystem.dtos.UserResponse;
import com.facturation.system.facturationsystem.models.UserModel;
import com.facturation.system.facturationsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Crear usuario
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserModel user) {
        try {
            UserModel savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // Obtener todos los usuarios (en producción, usa DTOs sin password)
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getRol()))
                .toList();
        return ResponseEntity.ok(users);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(
            @PathVariable Long id,
            @RequestBody UserModel userDetails) {
        return userService.getUserById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userDetails.getUsername());
                    existingUser.setRol(userDetails.getRol());
                    // Solo actualiza la contraseña si se proporciona
                    if (userDetails.getPassword() != null) {
                        existingUser.setPassword(userDetails.getPassword());
                    }
                    UserModel updatedUser = userService.saveUser(existingUser);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
