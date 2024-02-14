package com.sericulture.masterdata.model.dto.govtSmsService;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GovtSmsServiceDTO {
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Username must contain only letters and numbers")
    private String username;
    private String password;
    private String message;
    private String senderId;
    @Pattern(regexp = "^[0-9+\\s]*$", message = "Phone number must contain only numbers")
    private String mobileNumber;
    private String secureKey;
    private String templateid;

    private String userId;
}
