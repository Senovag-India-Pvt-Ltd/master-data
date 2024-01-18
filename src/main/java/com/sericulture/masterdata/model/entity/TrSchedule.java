package com.sericulture.masterdata.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class TrSchedule extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TR_SCHEDULE_SEQ")
    @SequenceGenerator(name = "TR_SCHEDULE_SEQ", sequenceName = "TR_SCHEDULE_SEQ", allocationSize = 1)
    @Column(name = "TR_SCHEDULE_ID")
    private Long trScheduleId;

    @Column(name = "TR_INSTITUTION_MASTER_ID")
    private Long trInstitutionMasterId;

    @Column(name = "TR_GROUP_MASTER_ID")
    private Long trGroupMasterId;

    @Column(name = "TR_PROGRAM_MASTER_ID")
    private Long trProgramMasterId;

    @Column(name = "TR_COURSE_MASTER_ID")
    private Long trCourseMasterId;

    @Column(name = "TR_MODE_MASTER_ID")
    private Long trModeMasterId;

    @Column(name = "TR_DURATION")
    private Long trDuration;

    @Column(name = "TR_PERIOD")
    private Long trPeriod;

    @Column(name = "TR_NO_OF_PARTICIPANT")
    private Long trNoOfParticipant;

    @Column(name = "TR_START_DATE")
    private Date trStartDate;

    @Column(name = "TR_DATE_OF_COMPLETION")
    private Date trDateOfCompletion;




    @Size(min = 2, max = 250, message = "Tr Schedule name should be more than 1 characters.")
    @Column(name = "TR_NAME", unique = true)
    private String trName;

    @Column(name = "TR_UPLOAD_PATH", unique = true)
    private String trUploadPath;

}
