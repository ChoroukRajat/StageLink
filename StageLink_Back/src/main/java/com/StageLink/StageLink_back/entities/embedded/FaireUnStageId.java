package com.StageLink.StageLink_back.entities.embedded;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaireUnStageId implements Serializable {
    private Long idOffre;
    private Long idChefFiliere;
    private Long idEtudiant;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FaireUnStageId that = (FaireUnStageId) o;
        return Objects.equals(idOffre, that.idOffre) &&
                Objects.equals(idChefFiliere, that.idChefFiliere) &&
                Objects.equals(idEtudiant, that.idEtudiant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOffre, idChefFiliere, idEtudiant);
    }
}

