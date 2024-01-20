package com.sericulture.masterdata.model.entity;


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
public class TrTrainee extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TR_TRAINEE_SEQ")
    @SequenceGenerator(name = "TR_TRAINEE_SEQ", sequenceName = "TR_TRAINEE_SEQ", allocationSize = 1)
    @Column(name = "TR_TRAINEE_ID")
    private Long trTraineeId;

    @Column(name = "TR_SCHEDULE_ID")
    private Long trScheduleId;

    @Size(min = 2, max = 250, message = "Tr Trainee name should be more than 1 characters.")
    @Column(name = "TR_TRAINEE_NAME")
    private String trTraineeName;

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

    @Column(name = "PERCENTAGE_IMPOROVED")
    private BigDecimal percentageImproved;


    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "PLACE")
    private String place;

}
