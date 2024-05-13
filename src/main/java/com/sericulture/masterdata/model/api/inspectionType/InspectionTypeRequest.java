package com.sericulture.masterdata.model.api.inspectionType;

import com.sericulture.masterdata.model.api.RequestBody;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class InspectionTypeRequest extends RequestBody {


    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Name must contain only letters and numbers")
    @Schema(name = "name",  example = "Karnataka",required=true)
    String name;


    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Name In Kannada must contain only letters and numbers")
    @Schema(name = "nameInKannada", example = "Karnataka",required=true)
    String nameInKannada;

    @Schema(name = "value", example = "1")
    Long value;

    @Schema(name = "version", example = "1")
    Long version;

}
