package com.StageLink.StageLink_back.repositories;

import com.StageLink.StageLink_back.entities.Encadrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EncadrantRepository extends JpaRepository<Encadrant, Long> {
}
