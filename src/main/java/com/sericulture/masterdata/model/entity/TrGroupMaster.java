package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
public class TrGroupMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TR_GROUP_MASTER_SEQ")
    @SequenceGenerator(name = "TR_GROUP_MASTER_SEQ", sequenceName = "TR_GROUP_MASTER_SEQ", allocationSize = 1)
    @Column(name = "TR_GROUP_MASTER_ID")
    private Long trGroupMasterId;


    @Size(min = 2, max = 250, message = "Tr Group Master name should be more than 1 characters.")
    @Column(name = "TR_GROUP_MASTER_NAME", unique = true)
    private String trGroupMasterName;

    @Column(name = "TR_GROUP_NAME_IN_KANNADA", unique = true)
    private String trGroupNameInKannada;
}
