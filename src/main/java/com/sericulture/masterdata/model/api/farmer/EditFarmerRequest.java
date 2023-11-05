package com.sericulture.masterdata.model.api.farmer;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EditFarmerRequest extends RequestBody {
    @Schema(name = "farmerId", example = "1")
    Long farmerId;

    @Schema(name = "farmerNumber", example = "12345")
    String farmerNumber;

    @Schema(name = "fruitsId", example = "1")
    String fruitsId;

    @Schema(name = "firstName", example = "First name")
    String firstName;

    @Schema(name = "middleName", example = "Middle name")
    String middleName;

    @Schema(name = "lastName", example = "Last name")
    String lastName;

    @Schema(name = "dob", example = "2023-11-03 16:27:35.907")
    Date dob;

    @Schema(name = "genderId", example = "1")
    Long genderId;

    @Schema(name = "casteId", example = "1")
    Long casteId;

    @Schema(name = "casteId", example = "0")
    Boolean differentlyAbled;

    @Schema(name = "email", example = "example@xyz.com")
    String email;

    @Schema(name = "mobileNumber", example = "9876543210")
    String mobileNumber;

    @Schema(name = "aadhaarNumber", example = "111122223333")
    String aadhaarNumber;

    @Schema(name = "epicNumber", example = "1")
    String epicNumber;

    @Schema(name = "rationCardNumber", example = "12345")
    String rationCardNumber;

    @Schema(name = "totalLandHolding", example = "100")
    String totalLandHolding;

    @Schema(name = "passbookNumber", example = "1001001000100")
    String passbookNumber;

    @Schema(name = "landHoldingCategoryId", example = "1")
    Long landHoldingCategoryId;

    @Schema(name = "educationId", example = "1")
    Long educationId;

    @Schema(name = "representativeId", example = "1")
    Long representativeId;

    @Schema(name = "khazaneRecipientId", example = "1")
    String khazaneRecipientId;

    @Schema(name = "photoPath", example = "/example.jpg")
    String photoPath;
}