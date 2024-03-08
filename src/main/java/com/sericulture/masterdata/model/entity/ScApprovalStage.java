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
public class ScApprovalStage extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_approval_stage_seq")
    @SequenceGenerator(name = "sc_approval_stage_seq", sequenceName = "sc_approval_stage_seq", allocationSize = 1)
    @Column(name = "sc_approval_stage_id")
    private Long scApprovalStageId;


    @Size(min = 2, max = 250, message = "Sc Approval name should be more than 1 characters.")
    @Column(name = " stage_name", unique = true)
    private String stageName;

    @Column(name = " stage_name_in_kannada", unique = true)
    private String stageNameInKannada;
}
