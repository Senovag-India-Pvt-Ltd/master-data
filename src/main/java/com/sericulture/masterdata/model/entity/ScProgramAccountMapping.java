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
public class ScProgramAccountMapping extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_program_account_mapping_seq")
    @SequenceGenerator(name = "sc_program_account_mapping_seq", sequenceName = "sc_program_account_mapping_seq", allocationSize = 1)
    @Column(name = "sc_program_account_mapping_id")
    private Long scProgramAccountMappingId;

    @Column(name = "sc_program_id")
    private Long scProgramId;

    @Column(name = "sc_head_account_id")
    private Long scHeadAccountId;

    @Column(name = "sc_category_id")
    private Long scCategoryId;

}
