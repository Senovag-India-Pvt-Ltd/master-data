package com.sericulture.masterdata.model.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CasteDTO {

    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Caste title must contain only letters and numbers")
    private String Caste;

    public CasteDTO() {
    }

    public CasteDTO(String caste) {
        Caste = caste;
    }
}
