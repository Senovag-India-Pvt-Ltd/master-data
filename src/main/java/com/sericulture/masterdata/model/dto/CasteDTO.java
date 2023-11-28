package com.sericulture.masterdata.model.dto;

import lombok.Data;

@Data
public class CasteDTO {
    private String Caste;

    public CasteDTO() {
    }

    public CasteDTO(String caste) {
        Caste = caste;
    }
}
