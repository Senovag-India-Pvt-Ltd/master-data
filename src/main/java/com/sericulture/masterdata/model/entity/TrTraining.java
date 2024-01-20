package com.sericulture.masterdata.model.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TrTraining extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TR_TRAINING_SEQ")
    @SequenceGenerator(name = "TR_TRAINING_SEQ", sequenceName = "TR_TRAINING_SEQ", allocationSize = 1)
    @Column(name = "TR_TRAINING_ID")
    private Long trTrainingId;

    @Column(name = "TR_STAKEHOLDER_TYPE")
    private Long trStakeholderType;

    @Column(name = "TR_INSTITUTION_MASTER_ID")
    private Long trInstitutionMasterId;

    @Column(name = "TR_GROUP_MASTER_ID")
    private Long trGroupMasterId;

    @Column(name = "TR_PROGRAM_MASTER_ID")
    private Long trProgramMasterId;

    @Column(name = "TR_COURSE_MASTER_ID")
    private Long trCourseMasterId;

    @Column(name = "TR_TRAINING_DATE")
    private Date trTrainingDate;

    @Column(name = "TR_DURATION")
    private Long trDuration;

    @Column(name = "TR_PERIOD")
    private Long trPeriod;

    @Column(name = "USER_MASTER_ID")
    private Long userMasterId;

}
