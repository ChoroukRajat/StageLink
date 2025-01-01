package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.FaireUnStage;
import com.StageLink.StageLink_back.entities.embedded.FaireUnStageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FaireUnStageRepository extends JpaRepository<FaireUnStage, FaireUnStageId> {
}
