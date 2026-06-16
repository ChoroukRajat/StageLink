package com.StageLink.StageLink_back.controllers;

import com.StageLink.StageLink_back.services.EcoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ecoles")
public class EcoleController {
    private final EcoleService ecoleService;

    public EcoleController(EcoleService ecoleService) {
        this.ecoleService = ecoleService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllEcoles() {
        List<Map<String, Object>> ecoles = ecoleService.getAllEcoles();
        return ResponseEntity.ok(ecoles);
    }
}

