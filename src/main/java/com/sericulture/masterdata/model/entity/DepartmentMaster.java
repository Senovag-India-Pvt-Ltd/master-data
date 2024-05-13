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
public class DepartmentMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTMENT_MASTER_seq")
    @SequenceGenerator(name = "DEPARTMENT_MASTER_seq", sequenceName = "DEPARTMENT_MASTER_seq", allocationSize = 1)
    @Column(name = "DEPARTMENT_MASTER_ID")
    private Long departmentId;

    @Column(name = "DEPT_CODE")
    private String departmentCode;

    @Column(name = "NAME")
    private String departmentName;

    @Column(name = "NAME_IN_KANNADA")
    private String departmentNameInKannada;

    @Column(name = "DEPT_DETAILS")
    private String departmentDetails;
}
