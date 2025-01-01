package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.Ecole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcoleRepository extends JpaRepository<Ecole, Long> {
}
