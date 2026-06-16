package com.StageLink.StageLink_back.mappers;

import com.StageLink.StageLink_back.dtos.UtilisateurDTO;
import com.StageLink.StageLink_back.entities.Utilisateur;

public class UserMapper {
    public static UtilisateurDTO toDTO(Utilisateur utilisateur) {
        if (utilisateur == null) {
            return null;
        }

        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setIdUtilisateur(utilisateur.getIdUtilisateur());
        dto.setEmail(utilisateur.getEmail());
        dto.setPassword(utilisateur.getPassword());
        dto.setRole(utilisateur.getRole() != null ? utilisateur.getRole().getNomRole() : null);
        return dto;
    }
}
