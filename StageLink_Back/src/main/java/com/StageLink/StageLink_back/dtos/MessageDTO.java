package com.StageLink.StageLink_back.dtos;

import lombok.Data;

@Data
public class MessageDTO {
    private Long idMessage;
    private Long utilisateurId;
    private String corpsMsg;
    private boolean lu;
}

