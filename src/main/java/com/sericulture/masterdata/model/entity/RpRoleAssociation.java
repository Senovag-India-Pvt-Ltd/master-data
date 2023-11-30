package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RpRoleAssociation extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rp_role_association_seq")
    @SequenceGenerator(name = "rp_role_association_seq", sequenceName = "rp_role_association_seq", allocationSize = 1)
    @Column(name = "rp_role_association_id")
    private Long rpRoleAssociationId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "rp_role_permission_id")
    private Long rpRolePermissionId;

}
