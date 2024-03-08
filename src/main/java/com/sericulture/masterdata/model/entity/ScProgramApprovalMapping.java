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
public class ScProgramApprovalMapping extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_program_approval_mapping_seq")
    @SequenceGenerator(name = "sc_program_approval_mapping_seq", sequenceName = "sc_program_approval_mapping_seq", allocationSize = 1)
    @Column(name = "sc_program_approval_mapping_id")
    private Long scProgramApprovalMappingId;

    @Column(name = "sc_program_id")
    private Long scProgramId;

    @Column(name = "sc_approval_stage_id")
    private Long scApprovalStageId;

    @Column(name = "designation_id")
    private Long designationId;
}
