package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {
}
