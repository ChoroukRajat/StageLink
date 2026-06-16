package com.StageLink.StageLink_back.controllers;


import com.StageLink.StageLink_back.dtos.ChefFiliereDTO;
import com.StageLink.StageLink_back.entities.ChefFiliere;
import com.StageLink.StageLink_back.entities.Offre;

import com.StageLink.StageLink_back.entities.OffreSelectionnee;
import com.StageLink.StageLink_back.mappers.CFMapper;
import com.StageLink.StageLink_back.repositories.ChefFiliereRepository;
import com.StageLink.StageLink_back.repositories.OffreSelectionneRepository;
import com.StageLink.StageLink_back.security.JwtUtils;
import com.StageLink.StageLink_back.services.OffreSelectionneService;
import com.StageLink.StageLink_back.services.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role/cf")
public class CFController {

    @Autowired
    ChefFiliereRepository cfRepository;



    @Autowired
    private OffreSelectionneRepository repository;

    @Autowired
    private OffreService offreService;

    @Autowired
    private OffreSelectionneService offreSelectionneService;

    @GetMapping("/{id}")
    public ResponseEntity<ChefFiliereDTO> getCFByUtilisateurId(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader) {

        // Validate the Authorization Header
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        if (JwtUtils.validateToken(token) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        ChefFiliere cf = cfRepository.findByUtilisateurId(id);

        if (cf != null) {
            ChefFiliereDTO cfDTO = CFMapper.toDTO(cf);
            return ResponseEntity.ok(cfDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // API: Get filtered and sorted offers
    @GetMapping("/offres")
    public ResponseEntity<List<Offre>> getFilteredOffres(
             // Unused for now but can validate access based on `idcf`
            @RequestParam(required = false) Long idVille,
            @RequestParam(defaultValue = "true") boolean isAscending) {
        List<Offre> offres = offreService.getFilteredOffres(idVille, isAscending);
        return ResponseEntity.ok(offres);
    }

    @GetMapping("/offres/{idOffre}")
    public ResponseEntity<Offre> getOffreDetails(
             // Unused for now but can validate access based on `idcf`
            @PathVariable Long idOffre) {
        Optional<Offre> offre = offreService.getOffreDetails(idOffre);
        return offre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{idcf}/offres/os")
    public ResponseEntity<String> createOffreSelectionnes(
            @PathVariable Long idcf,
            @RequestBody List<Long> offreIds) {

        try {
            offreSelectionneService.saveOffreSelectionnes(idcf, offreIds);
            return ResponseEntity.ok("Selected offers have been successfully saved.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/offreSelected")
    public ResponseEntity<List<Offre>> getOffresByChefFiliereId(@RequestParam("idChefFiliere") Long idChefFiliere) {
        List<Offre> offres = offreSelectionneService.getOffresByChefFiliereId(idChefFiliere);

        if (offres.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 if no data is found
        }
        return ResponseEntity.ok(offres);
    }

}


