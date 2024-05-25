package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "reject_reason_workflow_master")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RejectReasonWorkFlowMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reject_reason_workflow_master_SEQ")
    @SequenceGenerator(name = "reject_reason_workflow_master_SEQ", sequenceName = "reject_reason_workflow_master_SEQ", allocationSize = 1)
    @Column(name = "reject_reason_workflow_master_id")
    private Long rejectReasonWorkFlowMasterId;

    @Column(name = "reason", unique = true)
    private String reason;

}
