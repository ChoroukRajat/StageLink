package com.StageLink.StageLink_back.controllers;

import com.StageLink.StageLink_back.entities.Candidature;
import com.StageLink.StageLink_back.repositories.CandidatureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidatures")
public class CandidatureController {

    @Autowired
    private CandidatureRepository candidatureRepository;

    // a refaire hadchi bach n mappi hna response l CandidatureDetailsDTO

    // Retrieve all candidature details
    @GetMapping("/{id}/details")
    public ResponseEntity<Candidature> getCandidatureDetails(@PathVariable Long id) {
        return candidatureRepository.findByIdCandidature(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    // Generate convention if valid
    @GetMapping("/{id}/convention")
    public ResponseEntity<?> generateConvention(@PathVariable Long id) {
        return candidatureRepository.findById(id).map(candidature -> {
            if (!candidature.isValide() || !candidature.isConfirmationEtudiant()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Candidature not valid or not confirmed by the student");
            }
            return ResponseEntity.ok(candidature); // Pass necessary data for the convention
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidature not found"));
    }

    // Generate attestation if evaluation is done
    @GetMapping("/{id}/attestation")
    public ResponseEntity<?> generateAttestation(@PathVariable Long id) {
        return candidatureRepository.findById(id).map(candidature -> {
            if (candidature.getEvaluation() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Attestation cannot be generated, evaluation missing");
            }
            return ResponseEntity.ok(candidature); // Pass necessary data for the attestation
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidature not found"));
    }
}

