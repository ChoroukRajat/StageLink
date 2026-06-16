package com.StageLink.StageLink_back.controllers;


import com.StageLink.StageLink_back.dtos.AttestationDTO;
import com.StageLink.StageLink_back.entities.Candidature;
import com.StageLink.StageLink_back.repositories.CandidatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
public class DocumentsController {

    @Autowired
    CandidatureRepository candidatureRepository;



    @GetMapping("/{id}")
    public ResponseEntity<?> getInfoCAttestation(@PathVariable Long id){
        Candidature candidature = candidatureRepository.findByIdCandidature(id).get();

        if(!candidature.isValide() || !candidature.isConfirmationEtudiant()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }

        AttestationDTO cnvDTO = new AttestationDTO();
        cnvDTO.setCompanyName(candidature.getOffre().getEncadrant().getEntreprise().getNomEntreprise());
        cnvDTO.setConfirmationEtudiant(candidature.isConfirmationEtudiant());
        cnvDTO.setDescriptionOffre(candidature.getOffre().getDescription());
        cnvDTO.setEncadrantNom(candidature.getOffre().getEncadrant().getNom());
        cnvDTO.setDuree(candidature.getOffre().getDuree());
        cnvDTO.setOffreTitle(candidature.getOffre().getTitreProjet());
        cnvDTO.setValide(candidature.isValide());
        cnvDTO.setRemuneration(candidature.getOffre().isRemuneration());
        cnvDTO.setStudentEmail(candidature.getEtudiant().getUtilisateur().getEmail());
        cnvDTO.setStudentNom(candidature.getEtudiant().getNom());
        cnvDTO.setStudentPrenom(candidature.getEtudiant().getPrenom());
        cnvDTO.setEncadrantPrenom(candidature.getOffre().getEncadrant().getPrenom());
        cnvDTO.setObjectifsProjet(candidature.getOffre().getObjectifsProjet());
        cnvDTO.setEvaluation(candidature.getEvaluation());
        cnvDTO.setAdresseEcole(candidature.getEtudiant().getChefFiliere().getEcole().getAdresseEcole());
        cnvDTO.setNomEcole(candidature.getEtudiant().getChefFiliere().getEcole().getNomEcole());
        cnvDTO.setTelephoneEcole(candidature.getEtudiant().getChefFiliere().getEcole().getTelephoneEcole());
        cnvDTO.setCompetence(candidature.getOffre().getCompetence());
        cnvDTO.setCfNom(candidature.getEtudiant().getChefFiliere().getNom());
        cnvDTO.setCfPrenom(candidature.getEtudiant().getChefFiliere().getPrenom());
        cnvDTO.setNomFiliere(candidature.getEtudiant().getChefFiliere().getNomFiliere());
        cnvDTO.setAdresseEntreprise(candidature.getOffre().getEncadrant().getEntreprise().getAdresseEntreprise());
        cnvDTO.setTelephoneEntreprise(candidature.getOffre().getEncadrant().getEntreprise().getTelephoneEntreprise());
        cnvDTO.setFaxEntreprise(candidature.getOffre().getEncadrant().getEntreprise().getFaxEntreprise());
        cnvDTO.setType(candidature.getOffre().getType().getNomType());
        cnvDTO.setPromotion(candidature.getEtudiant().getAnneePromotion());




        return ResponseEntity.ok(cnvDTO);
    }

}
