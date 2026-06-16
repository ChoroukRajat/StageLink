package com.StageLink.StageLink_back.mappers;

import com.StageLink.StageLink_back.dtos.EncadrantDTO;
import com.StageLink.StageLink_back.entities.Encadrant;
import com.StageLink.StageLink_back.entities.Entreprise;
import com.StageLink.StageLink_back.entities.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class EncadrantMapper {

    // Convert Encadrant to EncadrantDTO
    public static EncadrantDTO toDTO(Encadrant encadrant) {
        if (encadrant == null) {
            return null;
        }

        EncadrantDTO dto = new EncadrantDTO();
        dto.setIdEncadrant(encadrant.getIdEncadrant());
        dto.setProfession(encadrant.getProfession());
        dto.setDepartement(encadrant.getDepartement());
        dto.setNom(encadrant.getNom());
        dto.setPrenom(encadrant.getPrenom());
        dto.setEntrepriseId(encadrant.getEntreprise().getIdEntreprise()); // Assuming Entreprise has a getId() method
        dto.setUtilisateurId(encadrant.getUtilisateur().getIdUtilisateur()); // Assuming Utilisateur has a getId() method
        return dto;
    }


}
