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
public class TsActivityMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ts_activity_master_seq")
    @SequenceGenerator(name = "ts_activity_master_seq", sequenceName = "ts_activity_master_seq", allocationSize = 1)
    @Column(name = "ts_activity_master_id")
    private Long tsActivityMasterId;


    @Size(min = 2, max = 250, message = "Activity name should be more than 1 characters.")
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "name_in_kannada", unique = true)
    private String nameInKannada;

    @Column(name = "code", unique = true)
    private String code;
}
