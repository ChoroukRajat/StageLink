package com.StageLink.StageLink_back.services;

import com.StageLink.StageLink_back.dtos.CandidatureDTO;
import com.StageLink.StageLink_back.entities.Candidature;
import com.StageLink.StageLink_back.entities.Message;
import com.StageLink.StageLink_back.repositories.CandidatureRepository;
import com.StageLink.StageLink_back.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CandidatureService {

    @Autowired
    private CandidatureRepository candidatureRepository;


    public List<CandidatureDTO> getCandidaturesByEncadrant(
            Long idEncadrant, Long idEcole, Boolean isValide, Long idTypeOffre, String orderByDate) {

        // Fetch the candidatures
        List<Candidature> candidatures = candidatureRepository.findCandidaturesByEncadrant(
                idEncadrant, idEcole, isValide, idTypeOffre);

        // Sort the list based on orderByDate
        if ("asc".equalsIgnoreCase(orderByDate)) {
            candidatures.sort(Comparator.comparing(Candidature::getCreatedDate));
        } else {
            candidatures.sort(Comparator.comparing(Candidature::getCreatedDate).reversed());
        }

        // Map to DTOs and return
        return candidatures.stream().map(candidature -> {
            CandidatureDTO dto = new CandidatureDTO();
            dto.setIdCandidature(candidature.getIdCandidature());
            dto.setOffreSelectionneeId(candidature.getOffre().getIdOffre());
            dto.setEtudiantId(candidature.getEtudiant().getIdEtudiant());
            dto.setValide(candidature.isValide());
            dto.setConfirmationEtudiant(candidature.isConfirmationEtudiant());
            dto.setEvaluation(candidature.getEvaluation());
            dto.setCreateDate(candidature.getCreatedDate());
            dto.setNomEtudiant(candidature.getEtudiant().getNom());
            dto.setPrenomEtudiant(candidature.getEtudiant().getPrenom());
            dto.setTitreProjet(candidature.getOffre().getTitreProjet());
            return dto;
        }).collect(Collectors.toList());
    }


    public CandidatureDTO getCandidatureDetails(Long idCandidature) {
        Candidature candidature = candidatureRepository.findById(idCandidature)
                .orElseThrow(() -> new RuntimeException("Candidature not found"));

        CandidatureDTO dto = new CandidatureDTO();

        // Set core fields
        dto.setIdCandidature(candidature.getIdCandidature());
        //dto.setOffreSelectionneeId(candidature.getOffreSelectionnee().getIdOffreSelectionnee());
        dto.setEtudiantId(candidature.getEtudiant().getIdEtudiant());
        dto.setValide(candidature.isValide());
        dto.setConfirmationEtudiant(candidature.isConfirmationEtudiant());
        dto.setEvaluation(candidature.getEvaluation());
        dto.setCreateDate(candidature.getCreatedDate());

        return dto;
    }

//    public String updateCandidature(Long idCandidature, CandidatureDTO updatedCandidatureDTO) {
//        Candidature candidature = candidatureRepository.findById(idCandidature)
//                .orElseThrow(() -> new RuntimeException("Candidature not found"));
//
//
//        candidature.setValide(updatedCandidatureDTO.isValide());
//        candidature.setEvaluation(updatedCandidatureDTO.getEvaluation());
//
//        candidatureRepository.save(candidature);
//        return "Candidature updated successfully.";
//    }

    public String updateCandidature(Long idCandidature, CandidatureDTO updatedCandidatureDTO) {
        // Fetch the candidature by ID
        Candidature candidature = candidatureRepository.findById(idCandidature)
                .orElseThrow(() -> new RuntimeException("Candidature not found"));

        // Check if any changes are made to isValide or evaluation
        boolean isValideChanged = candidature.isValide() != updatedCandidatureDTO.isValide();
        boolean isEvaluationChanged = !Objects.equals(candidature.getEvaluation(), updatedCandidatureDTO.getEvaluation());

        // Update the candidature fields
        candidature.setValide(updatedCandidatureDTO.isValide());
        candidature.setEvaluation(updatedCandidatureDTO.getEvaluation());
        candidatureRepository.save(candidature);

        return "Candidature updated successfully.";
    }





}
