package com.StageLink.StageLink_back.controllers;

import com.StageLink.StageLink_back.dtos.EtudiantDTO;
import com.StageLink.StageLink_back.dtos.OffreDTO;
import com.StageLink.StageLink_back.entities.Etudiant;
import com.StageLink.StageLink_back.entities.Offre;
import com.StageLink.StageLink_back.mappers.EtudiantMapper;
import com.StageLink.StageLink_back.mappers.OffreMapper;
import com.StageLink.StageLink_back.repositories.CandidatureRepository;
import com.StageLink.StageLink_back.repositories.EtudiantRepository;
import com.StageLink.StageLink_back.repositories.OffreRepository;
import com.StageLink.StageLink_back.security.JwtUtils;

import com.StageLink.StageLink_back.services.OffreService;
import com.StageLink.StageLink_back.entities.Candidature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


import java.util.stream.Collectors;

@RestController
@RequestMapping("/role/etudiant")
public class EtudiantController {

    @Autowired
    EtudiantRepository etudiantRepository;
    @Autowired
    OffreService offreService;

    @Autowired
    OffreRepository offreRepository;


    @Autowired
    private CandidatureRepository candidatureRepository;


    @GetMapping("/{id}")
    public ResponseEntity<EtudiantDTO> getEtudiantByUtilisateurId(
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

        // Query the Etudiant table using the id_utilisateur

        Etudiant etudiant = etudiantRepository.findByUtilisateurId(id);

        // If the Etudiant is found, map it to a DTO and return
        if (etudiant != null) {
            EtudiantDTO etudiantDTO = EtudiantMapper.toDTO(etudiant);
            return ResponseEntity.ok(etudiantDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


//    @GetMapping("/{id}/offres")
//    public ResponseEntity<List<OffreDTO>> getOffresSelectionneesByUtilisateur(
//            @PathVariable Long idUtilisateur,
//            @RequestHeader("Authorization") String authorizationHeader) {
//
//        // Authorization Header Validation
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//
//        String token = authorizationHeader.substring(7);
//        if (JwtUtils.validateToken(token) == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }
//
//        // Fetch the Etudiant by id_utilisateur
//        Etudiant etudiant = etudiantRepository.findByUtilisateurId(idUtilisateur);
//        if (etudiant == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Fetch the selected offers for the Etudiant
//        List<Offre> offres = offreService.getOffresSelectionneesByEtudiant(etudiant.getIdEtudiant());
//
//        // Map to DTOs
//        List<OffreDTO> offreDTOs = offres.stream().map(OffreMapper::toDTO).toList();
//        return ResponseEntity.ok(offreDTOs);
//    }

    //changer à ce que cet api ne retourne que les offres que l'étudiant n'a pas postulé sa candidature (de meme pour le chef de filiere)
    @GetMapping("/{idUtilisateur}/offres")
    public ResponseEntity<List<OffreDTO>> getOffresSelectionneesByUtilisateur(
            @PathVariable Long idUtilisateur,
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(required = false) String dateSort,  // Ascendant ou Descendant
            @RequestParam(required = false) Long idVille) {  // ID de la ville

        // Authorization Header Validation
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String token = authorizationHeader.substring(7);
        if (JwtUtils.validateToken(token) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // Fetch the Etudiant by id_utilisateur
        Etudiant etudiant = etudiantRepository.findByUtilisateurId(idUtilisateur);
        if (etudiant == null) {
            return ResponseEntity.notFound().build();
        }

        // Fetch the selected offers for the Etudiant
        List<Offre> offres = offreService.getOffresSelectionneesByEtudiant(etudiant.getIdEtudiant());

        // Apply filtering by date if provided
        if (dateSort != null) {
            if (dateSort.equalsIgnoreCase("ascendant")) {
                offres.sort(Comparator.comparing(Offre::getCreatedDate));  // Tri ascendant par date
            } else if (dateSort.equalsIgnoreCase("descendant")) {
                offres.sort(Comparator.comparing(Offre::getCreatedDate).reversed());  // Tri descendant par date
            }
        }

        // Apply filtering by city ID if provided
        if (idVille != null) {
            offres = offres.stream()
                    .filter(offre -> offre.getVille().getIdVille().equals(idVille))
                    .collect(Collectors.toList());
        }

        // Map to DTOs
        List<OffreDTO> offreDTOs = offres.stream().map(OffreMapper::toDTO).toList();

        return ResponseEntity.ok(offreDTOs);
    }



    @PostMapping("/{id}/candidature")
    public ResponseEntity<?> createCandidature(
            @PathVariable Long id,
            @RequestParam("idOffre") Long idOffre,
            @RequestParam("cv") String cvUrl,
            @RequestParam("motivation") String motivationUrl) {

        try {
            // Retrieve Etudiant using idUtilisateur
            Etudiant etudiant = etudiantRepository.findByUtilisateurId(id);
            if (etudiant == null) {
                return ResponseEntity.notFound().build(); // Return 404 if the Etudiant is not found
            }

            // Retrieve Offre using idOffre
            Optional<Offre> offre = offreRepository.findById(idOffre);
            if (offre.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Offre ID");
            }

            // Create and save Candidature entity
            Candidature candidature = new Candidature();
            candidature.setEtudiant(etudiant);
            candidature.setValide(false);
            candidature.setConfirmationEtudiant(false);
            candidature.setOffre(offre.get());
            candidature.setCvUrl(cvUrl);
            candidature.setMotivationUrl(motivationUrl);

            candidatureRepository.save(candidature);

            return ResponseEntity.status(HttpStatus.CREATED).body("Candidature created successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating candidature: " + e.getMessage());
        }
    }

//7ed hna lcode dyal creer candidature




    //ou hada update candidature : tan updati l filestorageservice
    @PostMapping("/{id}/candidature/{idCandidature}")
    public ResponseEntity<?> updateCandidatureWithRapportOrConfirmation(
            @PathVariable Long id,
            @PathVariable Long idCandidature,
            @RequestParam(value = "rapport", required = false) String rapportUrl,
            @RequestParam(value = "confirmationEtudiant", required = false) Boolean confirmationEtudiant) {

        try {
            // Retrieve the Candidature using its ID
            Candidature candidature = candidatureRepository.findById(idCandidature)
                    .orElseThrow(() -> new RuntimeException("Candidature not found"));

            // Update confirmationEtudiant if provided
            if (confirmationEtudiant != null && !candidature.isConfirmationEtudiant()) {
                candidature.setConfirmationEtudiant(confirmationEtudiant);
            }

            // Save the rapport file if provided
            if (rapportUrl != null) {
                // Upload rapport to OCI and get URL
                    candidature.setRapportUrl(rapportUrl);

            }

            // Save the updated Candidature entity
            candidatureRepository.save(candidature);

            return ResponseEntity.status(HttpStatus.OK).body("Candidature updated successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating candidature: " + e.getMessage());
        }
    }



    @GetMapping("/{id}/candidatures")
    public ResponseEntity<?> getCandidatures(
            @PathVariable Long id,
            @RequestParam(value = "id_candidature", required = false) Long idCandidature) {


        try {
            // Step 1: Resolve idEtudiant using idUtilisateur
            Etudiant etudiant = etudiantRepository.findByUtilisateurId(id);
            if (etudiant == null) {
                return ResponseEntity.notFound().build(); // Return 404 if the Etudiant is not found
            }
            // Step 2: If id_candidature is provided, fetch and display specific candidature details
            if (idCandidature != null) {
                Optional<Candidature> optionalCandidature = candidatureRepository.findByIdCandidature(idCandidature);

                if (optionalCandidature.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Candidature not found for ID: " + idCandidature);
                }

                Candidature candidature = optionalCandidature.get();

                // Ensure the candidature belongs to the student
                if (!candidature.getEtudiant().getIdEtudiant().equals(etudiant.getIdEtudiant())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Candidature does not belong to the specified student.");
                }

                // Prepare detailed response for the specific candidature
                Map<String, Object> details = new HashMap<>();
                details.put("idCandidature", candidature.getIdCandidature());
                details.put("dateCreation", candidature.getCreatedDate());
                details.put("titreOffre", candidature.getOffre().getTitreProjet());
                details.put("cv", candidature.getCvUrl());
                details.put("lettreMotivation", candidature.getMotivationUrl());
                details.put("offreDetails", candidature.getOffre());// Assuming offre entity has the details you need
                details.put("valide", candidature.isValide());
                details.put("confirmationEtudiant", candidature.isConfirmationEtudiant());

                return ResponseEntity.ok(details);
            }

            // Step 3: If id_candidature is not provided, fetch all candidatures for the student
            List<Candidature> candidatures = candidatureRepository.findByEtudiant(etudiant);

            // Prepare a simplified response for all candidatures
            List<Map<String, Object>> response = new ArrayList<>();
            for (Candidature candidature : candidatures) {
                Map<String, Object> candidatureData = new HashMap<>();
                candidatureData.put("idCandidature", candidature.getIdCandidature());
                candidatureData.put("dateCreation", candidature.getCreatedDate());
                candidatureData.put("titreOffre", candidature.getOffre().getTitreProjet());
                candidatureData.put("valide",candidature.isValide());
                candidatureData.put("confirmationEtudiant", candidature.isConfirmationEtudiant());
                candidatureData.put("rapportUrl", candidature.getRapportUrl());
                response.add(candidatureData);
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching candidatures: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/rapports")
    public ResponseEntity<?> getRapportsByPromotionAndChefFiliere(@PathVariable Long id) {
        try {
            // Étape 1 : Trouver l'étudiant correspondant à idUtilisateur
            Etudiant etudiant = etudiantRepository.findByUtilisateurId(id);
            if (etudiant == null) {
                return ResponseEntity.notFound().build(); // Return 404 if the Etudiant is not found
            }
            Long idChefFiliere = etudiant.getChefFiliere().getIdChefFiliere(); // ID du chef de filière
            int promotion = etudiant.getAnneePromotion();
            // Étape 2 : Récupérer tous les étudiants de la même promotion et du même chef de filière
            List<Etudiant> etudiantsSimilaires = etudiantRepository.findByChefFiliereIdAndAnneePromotion(idChefFiliere, promotion);

            // Étape 3 : Récupérer les candidatures et leurs rapports pour ces étudiants
            List<Map<String, Object>> rapports = new ArrayList<>();

            for (Etudiant etudiantSimilaire : etudiantsSimilaires) {
                List<Candidature> candidatures = candidatureRepository.findByEtudiant(etudiantSimilaire);

                for (Candidature candidature : candidatures) {

                    if (candidature.isValide() && candidature.getRapportUrl() != null) { // Vérifier si un rapport existe
                        Map<String, Object> rapportDetails = new HashMap<>();
                        rapportDetails.put("idEtudiant", etudiantSimilaire.getIdEtudiant());
                        rapportDetails.put("nomEtudiant", etudiantSimilaire.getNom());
                        rapportDetails.put("prenomEtudiant", etudiantSimilaire.getPrenom());
                        rapportDetails.put("idCandidature", candidature.getIdCandidature());
                        rapportDetails.put("titreOffre", candidature.getOffre().getTitreProjet());
                        rapportDetails.put("rapportLien", candidature.getRapportUrl());
                        rapports.add(rapportDetails);
                    }
                }
            }

            return ResponseEntity.ok(rapports);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération des rapports : " + e.getMessage());
        }
    }

}
