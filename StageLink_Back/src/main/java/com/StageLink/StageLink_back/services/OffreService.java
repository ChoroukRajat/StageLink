package com.StageLink.StageLink_back.services;

import com.StageLink.StageLink_back.entities.Offre;
import com.StageLink.StageLink_back.repositories.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OffreService {

    @Autowired
    private OffreRepository offreRepository;

    public List<Offre> getOffresSelectionneesByEtudiant(Long etudiantId) {
        return offreRepository.findOffresByEtudiantId(etudiantId);
    }

    public List<Offre> getFilteredOffres(Long idVille, boolean isAscending) {
        if (isAscending) {
            return offreRepository.findAllOffresFilterByVilleSortedByDateAsc(idVille);
        } else {
            return offreRepository.findAllOffresFilterByVilleSortedByDateDesc(idVille);
        }
    }

    public Optional<Offre> getOffreDetails(Long idOffre) {
        return offreRepository.findById(idOffre);
    }
}

