package com.StageLink.StageLink_back.services;

import com.StageLink.StageLink_back.dtos.UtilisateurDTO;
import com.StageLink.StageLink_back.entities.Role;
import com.StageLink.StageLink_back.entities.Utilisateur;
import com.StageLink.StageLink_back.repositories.RoleRepository;
import com.StageLink.StageLink_back.repositories.UtilisateurRepository;
import com.StageLink.StageLink_back.security.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AuthService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;


    public Utilisateur registerUser(UtilisateurDTO utilisateurDTO) {
        if (utilisateurRepository.existsByEmail(utilisateurDTO.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }



        // Map role
        Role role = roleRepository.findByNomRole(utilisateurDTO.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Hash the password using the custom hash function
        String hashedPassword = PasswordUtils.hashPassword(utilisateurDTO.getPassword());

        // Create a new user
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(utilisateurDTO.getEmail());
        utilisateur.setPassword(hashedPassword);
        utilisateur.setRole(role);

        // Save the user
        return utilisateurRepository.save(utilisateur);
    }


    public String loginUser(String email, String password) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Hash the input password and compare
        String hashedPassword = PasswordUtils.hashPassword(password);
        if (!hashedPassword.equals(utilisateur.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        // Return a success message or token (if needed)
        return "Login successful!";
    }

}

