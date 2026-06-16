package com.StageLink.StageLink_back.services;

import com.StageLink.StageLink_back.entities.Ecole;
import com.StageLink.StageLink_back.repositories.EcoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcoleService {

    private final EcoleRepository ecoleRepository;

    public EcoleService(EcoleRepository ecoleRepository) {
        this.ecoleRepository = ecoleRepository;
    }

    public List<Map<String, Object>> getAllEcoles() {
        return ecoleRepository.findAll().stream().map(ecole -> {
            Map<String, Object> ecoleMap = new HashMap<>();
            ecoleMap.put("idEcole", ecole.getIdEcole());
            ecoleMap.put("nomEcole", ecole.getNomEcole());
            return ecoleMap;
        }).toList(); // Use `toList()` for Java 16+ or `collect(Collectors.toList())` for older versions
    }
}

