package com.StageLink.StageLink_back.dtos;

import lombok.Data;

@Data
public class EcoleDTO {
    private Long idEcole;
    private String nomEcole;
    private String adresseEcole;
    private String telephoneEcole;
    private String emailEcole;
    private Long utilisateurId;
}

