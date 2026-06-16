package com.StageLink.StageLink_back.repositories;


import com.StageLink.StageLink_back.entities.Offre;
import com.StageLink.StageLink_back.entities.OffreSelectionnee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OffreSelectionneRepository extends JpaRepository<OffreSelectionnee, Long> {

    @Query("SELECT os.offre FROM OffreSelectionnee os WHERE os.chefFiliere.id = :idChefFiliere")
    List<Offre> findOffresByChefFiliereId(@Param("idChefFiliere") Long idChefFiliere);
}
