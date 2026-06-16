package com.StageLink.StageLink_back.mappers;

import com.StageLink.StageLink_back.dtos.ChefFiliereDTO;
import com.StageLink.StageLink_back.entities.ChefFiliere;
import com.StageLink.StageLink_back.entities.Ecole;
import com.StageLink.StageLink_back.entities.Utilisateur;

public class CFMapper {
    // Convert ChefFiliere to ChefFiliereDTO
    public static ChefFiliereDTO toDTO(ChefFiliere chefFiliere) {
        if (chefFiliere == null) {
            return null;
        }

        ChefFiliereDTO dto = new ChefFiliereDTO();
        dto.setIdChefFiliere(chefFiliere.getIdChefFiliere());
        dto.setNomFiliere(chefFiliere.getNomFiliere());
        dto.setDomaineDetude(chefFiliere.getDomaineDetude());
        dto.setNom(chefFiliere.getNom());
        dto.setPrenom(chefFiliere.getPrenom());
        dto.setEcoleId(chefFiliere.getEcole().getIdEcole()); // Assuming Ecole has a getId() method
        dto.setUtilisateurId(chefFiliere.getUtilisateur().getIdUtilisateur()); // Assuming Utilisateur has a getId() method
        return dto;
    }


}
