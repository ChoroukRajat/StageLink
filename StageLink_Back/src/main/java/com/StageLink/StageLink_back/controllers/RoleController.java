package com.StageLink.StageLink_back.controllers;

import com.StageLink.StageLink_back.dtos.UtilisateurDTO;
import com.StageLink.StageLink_back.entities.Utilisateur;
import com.StageLink.StageLink_back.repositories.UtilisateurRepository;
import com.StageLink.StageLink_back.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final UtilisateurRepository utilisateurRepository;

    public RoleController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/{nomRole}")
    public ResponseEntity<UtilisateurDTO> handleRole(
            @RequestParam("id") Long userId,
            @RequestHeader("Authorization") String token) {

        // Validate JWT token
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }

        String jwtToken = token.substring(7);  // Extract JWT token

        try {
            // Validate and extract role and user info from JWT token
            String roleFromToken = JwtUtils.extractRole(jwtToken);
            String emailFromToken = JwtUtils.extractUsername(jwtToken);

            // Find the user by ID
            Optional<Utilisateur> optionalUser = utilisateurRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.badRequest().body(null); // User not found
            }

            Utilisateur user = optionalUser.get();

            // Validate that the role matches the token's role
            if (!user.getRole().getNomRole().equalsIgnoreCase(roleFromToken)) {
                return ResponseEntity.status(403).body(null); // Forbidden
            }

            // Convert user to DTO
            UtilisateurDTO utilisateurDTO = new UtilisateurDTO(
                    user.getIdUtilisateur(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole().getNomRole()
            );

            return ResponseEntity.ok(utilisateurDTO);

        } catch (Exception e) {
            return ResponseEntity.status(401).body(null); // Invalid token
        }
    }
}
