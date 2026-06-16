package com.StageLink.StageLink_back.mappers;

import com.StageLink.StageLink_back.dtos.OffreDTO;
import com.StageLink.StageLink_back.entities.Offre;

public class OffreMapper {
    public static OffreDTO toDTO(Offre offre) {
        OffreDTO dto = new OffreDTO();
        dto.setIdOffre(offre.getIdOffre());
        dto.setTitreProjet(offre.getTitreProjet());
        dto.setDescription(offre.getDescription());
        dto.setDuree(offre.getDuree());
        dto.setObjectifsProjet(offre.getObjectifsProjet());
        dto.setCompetence(offre.getCompetence());
        dto.setRemuneration(offre.isRemuneration());
        dto.setCreateDate(offre.getCreatedDate());
        return dto;
    }
}

