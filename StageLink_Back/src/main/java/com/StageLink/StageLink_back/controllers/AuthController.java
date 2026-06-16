package com.StageLink.StageLink_back.controllers;

import com.StageLink.StageLink_back.dtos.LoginRequestDTO;
import com.StageLink.StageLink_back.dtos.UtilisateurDTO;
import com.StageLink.StageLink_back.entities.ChefFiliere;
import com.StageLink.StageLink_back.entities.Encadrant;
import com.StageLink.StageLink_back.entities.Etudiant;
import com.StageLink.StageLink_back.entities.Utilisateur;
import com.StageLink.StageLink_back.repositories.ChefFiliereRepository;
import com.StageLink.StageLink_back.repositories.EncadrantRepository;
import com.StageLink.StageLink_back.repositories.EtudiantRepository;
import com.StageLink.StageLink_back.repositories.UtilisateurRepository;
import com.StageLink.StageLink_back.security.JwtUtils;
import com.StageLink.StageLink_back.security.PasswordUtils;
import com.StageLink.StageLink_back.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UtilisateurRepository utilisateurRepository;

    private final EtudiantRepository etudiantRepository;
    private final EncadrantRepository encadrantRepository;
    private final ChefFiliereRepository chefFiliereRepository;

    public AuthController(AuthService authService, UtilisateurRepository utilisateurRepository, EtudiantRepository etudiantRepository,EncadrantRepository encadrantRepository,ChefFiliereRepository chefFiliereRepository ) {
        this.authService = authService;
        this.utilisateurRepository = utilisateurRepository;
        this.encadrantRepository = encadrantRepository;
        this.etudiantRepository = etudiantRepository;
        this.chefFiliereRepository = chefFiliereRepository;
    }

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UtilisateurDTO utilisateurDTO) {
        try {
            Utilisateur registeredUser = authService.registerUser(utilisateurDTO);
            return ResponseEntity.ok("User registered successfully with ID: " + registeredUser.getIdUtilisateur());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Registration failed: " + ex.getMessage());
        }
    }

//    // Endpoint for user login
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
//        String email = loginRequest.getEmail();
//        String password = loginRequest.getPassword();
//
//        // Find user by email using Optional
//        Optional<Utilisateur> optionalUser = utilisateurRepository.findByEmail(email);
//
//        // Handle case where user is not found
//        if (optionalUser.isEmpty()) {
//            return ResponseEntity.badRequest().body("Login failed: User not found");
//        }
//
//        Utilisateur user = optionalUser.get();
//
//        // Check if the provided password matches the stored password
//        if (!PasswordUtils.checkPassword(password, user.getPassword())) {
//            return ResponseEntity.badRequest().body("Login failed: Invalid credentials!");
//        }
//
//        // Generate JWT token
//        String token = JwtUtils.generateToken(user.getEmail(), user.getRole().getNomRole());
//
//        // Prepare dynamic URL
//        String nomRole = user.getRole().getNomRole(); // Assuming `Role` is a field in `Utilisateur` entity
//        Long userId = user.getIdUtilisateur();       // Assuming `idUtilisateur` is the user's unique ID
//        String dynamicUrl = "/role/" + nomRole.toLowerCase() + "?id=" + userId;
//
//        // Return token and dynamic URL
//        return ResponseEntity.ok("Login successful! Token: " + token + " Redirect to: " + dynamicUrl);
//    }


 
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest) {
        Optional<Utilisateur> optionalUser = utilisateurRepository.findByEmail(loginRequest.getEmail());

        if (optionalUser.isEmpty() ||
                !PasswordUtils.checkPassword(loginRequest.getPassword(), optionalUser.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }

        Utilisateur user = optionalUser.get();
        String token = JwtUtils.generateToken(user.getEmail(), user.getRole().getNomRole());
        String redirectUrl = "/role/"+user.getRole().getNomRole()+ "/" + user.getIdUtilisateur(); // Add user ID to the URL

        Etudiant etudiant = etudiantRepository.findByUtilisateurId(user.getIdUtilisateur());
        if(etudiant != null){
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "redirectUrl", redirectUrl,
                    "idUtilisateur", "" + user.getIdUtilisateur(),
                    "id", ""+etudiant.getIdEtudiant()
            ));

        }

        ChefFiliere cf = chefFiliereRepository.findByUtilisateurId(user.getIdUtilisateur());

        if(cf != null){
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "redirectUrl", redirectUrl,
                    "idUtilisateur", "" + user.getIdUtilisateur(),
                    "id", ""+cf.getIdChefFiliere()
            ));

        }

        Encadrant en = encadrantRepository.findByUtilisateurId(user.getIdUtilisateur());

        if(en != null){
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "redirectUrl", redirectUrl,
                    "idUtilisateur", "" + user.getIdUtilisateur(),
                    "id", ""+en.getIdEncadrant()
            ));

        }

        return ResponseEntity.ok(Map.of(
                "token", token,
                "redirectUrl", redirectUrl,
                "idUtilisateur", "" + user.getIdUtilisateur()
        ));
    }


}
