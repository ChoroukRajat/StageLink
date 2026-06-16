package com.StageLink.StageLink_back.mappers;

import com.StageLink.StageLink_back.dtos.EtudiantDTO;
import com.StageLink.StageLink_back.entities.Etudiant;

public class EtudiantMapper {
    public static EtudiantDTO toDTO(Etudiant etudiant) {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setIdEtudiant(etudiant.getIdEtudiant());
        dto.setAdresseEtudiant(etudiant.getAdresseEtudiant());
        dto.setAnneePromotion(etudiant.getAnneePromotion());
        dto.setMentionEtudiant(etudiant.getMentionEtudiant());
        dto.setAssuranceEtudiant(etudiant.isAssuranceEtudiant());
        dto.setUtilisateurId(etudiant.getUtilisateur().getIdUtilisateur());
        dto.setNom(etudiant.getNom());
        dto.setPrenom(etudiant.getPrenom());
        dto.setTelephone(etudiant.getTelephone());
        return dto;
    }
}

