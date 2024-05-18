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
public class UserHierarchyMapping extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_hierarchy_mapping_seq")
    @SequenceGenerator(name = "user_hierarchy_mapping_seq", sequenceName = "user_hierarchy_mapping_seq", allocationSize = 1)
    @Column(name = "user_hierarchy_mapping_id")
    private Long userHierarchyMappingId;

    @Column(name = "reportee_user_master_id", unique = true)
    private Long reporteeUserMasterId;

    @Column(name = "report_to_user_master_id")
    private Long reportToUserMasterId;
}
