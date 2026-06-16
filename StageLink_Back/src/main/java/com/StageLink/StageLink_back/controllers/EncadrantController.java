package com.StageLink.StageLink_back.controllers;

import com.StageLink.StageLink_back.dtos.CandidatureDTO;

import com.StageLink.StageLink_back.dtos.EncadrantDTO;
import com.StageLink.StageLink_back.entities.Encadrant;
import com.StageLink.StageLink_back.entities.Offre;
import com.StageLink.StageLink_back.mappers.EncadrantMapper;
import com.StageLink.StageLink_back.repositories.EncadrantRepository;
import com.StageLink.StageLink_back.repositories.EtudiantRepository;
import com.StageLink.StageLink_back.security.JwtUtils;
import com.StageLink.StageLink_back.services.CandidatureService;
import com.StageLink.StageLink_back.services.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role/encadrant")
public class EncadrantController {

    @Autowired
    private OffreService offreService;

    @Autowired
    private CandidatureService candidatureService;

    @Autowired
    EncadrantRepository encadrantRepository;

    @GetMapping("/{idencadrant}/infos")
    public ResponseEntity<EncadrantDTO> getEncadrantByUtilisateurId(
            @PathVariable Long idencadrant,
            @RequestHeader("Authorization") String authorizationHeader) {

        // Validate the Authorization Header
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        if (JwtUtils.validateToken(token) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Query the Etudiant table using the id_utilisateur


        Encadrant encadrant = encadrantRepository.findByUtilisateurId(idencadrant);

        // If the Etudiant is found, map it to a DTO and return
        if (encadrant != null) {
            EncadrantDTO etudiantDTO = EncadrantMapper.toDTO(encadrant);
            return ResponseEntity.ok(etudiantDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{idencadrant}")
    public ResponseEntity<List<CandidatureDTO>> getCandidaturesByEncadrant(
            @PathVariable Long idencadrant,
            @RequestParam(required = false) Long idEcole,
            @RequestParam(required = false) Boolean isValide,
            @RequestParam(required = false) Long idTypeOffre,
            @RequestParam(defaultValue = "desc") String orderByDate) {

        List<CandidatureDTO> candidatures = candidatureService.getCandidaturesByEncadrant(
                idencadrant, idEcole, isValide, idTypeOffre, orderByDate);

        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/{idencadrant}/{idcandidature}")
    public ResponseEntity<CandidatureDTO> getCandidatureDetails(
            @PathVariable Long idencadrant,
            @PathVariable Long idcandidature) {

        CandidatureDTO candidatureDetails = candidatureService.getCandidatureDetails(idcandidature);
        return ResponseEntity.ok(candidatureDetails);
    }


    @PostMapping("/{idencadrant}/{idcandidature}")
    public ResponseEntity<String> updateCandidature(
            @PathVariable Long idencadrant,
            @PathVariable Long idcandidature,
            @RequestBody CandidatureDTO updatedCandidatureDTO) {

        String message = candidatureService.updateCandidature(idcandidature, updatedCandidatureDTO);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/offres/{idOffre}")
    public ResponseEntity<Offre> getOffreDetails(
            // Unused for now but can validate access based on `idcf`
            @PathVariable Long idOffre) {
        Optional<Offre> offre = offreService.getOffreDetails(idOffre);
        return offre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
