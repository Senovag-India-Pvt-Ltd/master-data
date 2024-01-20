package com.sericulture.masterdata.model.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TrTraining extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TR_TRAINING_SEQ")
    @SequenceGenerator(name = "TR_TRAINING_SEQ", sequenceName = "TR_TRAINING_SEQ", allocationSize = 1)
    @Column(name = "TR_TRAINEE_ID")
    private Long trTraineeId;

    @Column(name = "DESIGNATION_ID")
    private Long designationId;

    @Column(name = "TR_OFFICE_ID")
    private Long trOfficeId;

    @Column(name = "GENDER")
    private Long gender;


    @Column(name = "STATE_ID")
    private Long stateId;

    @Column(name = "DISTRICT_ID")
    private Long districtId;

    @Column(name = "TALUK_ID")
    private Long talukId;

    @Column(name = "HOBLI_ID")
    private Long hobliId;

    @Column(name = "VILLAGE_ID")
    private Long villageId;

    @Column(name = "PRE_TEST_SCORE")
    private Long preTestScore;

    @Column(name = "POST_TEST_SCORE")
    private Long postTestScore;

    @Column(name = "PERCENTAGE_IMPROVED")
    private BigDecimal percentageImproved;

//    @Size(min = 2, max = 250, message = "Tr Training name should be more than 1 characters.")
    @Column(name = "TR_TRAINEE_NAME")
    private String trTraineeName;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "PLACE")
    private String place;

}
