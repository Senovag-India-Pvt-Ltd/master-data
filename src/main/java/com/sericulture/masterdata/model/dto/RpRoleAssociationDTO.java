package com.sericulture.masterdata.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpRoleAssociationDTO {
    private Long rpRoleAssociationId;
    private Long roleId;
    private Long rpRolePermissionId;
    private Long value;
}
