package com.StageLink.StageLink_back.services;

import com.StageLink.StageLink_back.entities.ChefFiliere;
import com.StageLink.StageLink_back.entities.Offre;
import com.StageLink.StageLink_back.entities.OffreSelectionnee;
import com.StageLink.StageLink_back.repositories.ChefFiliereRepository;
import com.StageLink.StageLink_back.repositories.OffreRepository;
import com.StageLink.StageLink_back.repositories.OffreSelectionneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OffreSelectionneService {

    @Autowired
    private OffreSelectionneRepository offreSelectionneRepository;

    @Autowired
    private ChefFiliereRepository chefFiliereRepository;

    @Autowired
    private OffreSelectionneRepository repository;


    @Autowired
    private OffreRepository offreRepository;

    public void saveOffreSelectionnes(Long idChefFiliere, List<Long> offreIds) {
        ChefFiliere chefFiliere = chefFiliereRepository.findById(idChefFiliere)
                .orElseThrow(() -> new IllegalArgumentException("Chef Filiere not found with ID: " + idChefFiliere));

        List<OffreSelectionnee> offreSelectionnes = new ArrayList<>();
        for (Long offreId : offreIds) {
            Offre offre = offreRepository.findById(offreId)
                    .orElseThrow(() -> new IllegalArgumentException("Offre not found with ID: " + offreId));

            OffreSelectionnee offreSelectionne = new OffreSelectionnee();
            offreSelectionne.setChefFiliere(chefFiliere);
            offreSelectionne.setOffre(offre);
            offreSelectionnes.add(offreSelectionne);
        }

        offreSelectionneRepository.saveAll(offreSelectionnes);
    }

    public List<Offre> getOffresByChefFiliereId(Long idChefFiliere) {
        return repository.findOffresByChefFiliereId(idChefFiliere);
    }
}
