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
public class TrCourseMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tr_course_seq")
    @SequenceGenerator(name = "tr_course_seq", sequenceName = "tr_course_seq", allocationSize = 1)
    @Column(name = "tr_course_id")
    private Long trCourseMasterId;


    @Size(min = 2, max = 250, message = "Course name should be more than 1 characters.")
    @Column(name = " tr_course_name", unique = true)
    private String trCourseMasterName;
}
