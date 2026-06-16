package com.StageLink.StageLink_back.dtos;

import lombok.Data;

@Data
public class EntrepriseDTO {
    private Long idEntreprise;
    private String nomEntreprise;
    private String formeJuridique;
    private String adresseEntreprise;
    private String telephoneEntreprise;
    private String faxEntreprise;
    private String emailEntreprise;
    private Long utilisateurId;
}

