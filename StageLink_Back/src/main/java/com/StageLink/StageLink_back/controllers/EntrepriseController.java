package com.StageLink.StageLink_back.controllers;

import com.StageLink.StageLink_back.entities.Entreprise;
import com.StageLink.StageLink_back.repositories.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/entreprise")
public class EntrepriseController {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @GetMapping("/{idEntreprise}")
    public ResponseEntity<Entreprise> getEntrepriseById(@PathVariable Long idEntreprise) {
        // Fetch the Entreprise using the id_entreprise
        Entreprise entreprise = entrepriseRepository.findById(idEntreprise)
                .orElse(null);

        // If the Entreprise is not found, return a 404 response
        if (entreprise == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Return the Entreprise entity as a response
        return ResponseEntity.ok(entreprise);
    }
}

