package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrainingDeputationTracker extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_deputation_tracker_seq")
    @SequenceGenerator(name = "training_deputation_tracker_seq", sequenceName = "training_deputation_tracker_seq", allocationSize = 1)
    @Column(name = "training_deputation_id")
    private Long trainingDeputationId;

    @Size(min = 2, max = 100, message = "Official name should be more than 1 characters.")
    @Column(name = "official_name", unique = true)
    private String officialName;

    @Column(name = "designation_id")
    private Long designationId;

    @Column(name = "official_address")
    private String officialAddress;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "deputed_institute_id")
    private Long deputedInstituteId;

    @Column(name = "deputed_from_date")
    private Date deputedFromDate;

    @Column(name = "deputed_to_date")
    private Date deputedToDate;

    @Column(name = "tr_program_id")
    private Long trProgramMasterId;

    @Column(name = "tr_course_id")
    private Long trCourseMasterId;

    @Column(name = "deputed_attended")
    private int deputedAttended;

    @Column(name = "deputed_remarks")
    private String deputedRemarks;
}
