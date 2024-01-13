package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name = "releer_type_master_master")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ReleerTypeMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "releer_type_master_seq")
    @SequenceGenerator(name = "releer_type_master_seq", sequenceName = "releer_type_master_seq", allocationSize = 1)
    @Column(name = "releer_type_master_id")
    private Long releerTypeMasterId;


    @Size(min = 2, max = 250, message = "releerTypeMaster name should be more than 1 characters.")
    @Column(name = "releer_type_master_name", unique = true)
    private String releerTypeMasterName;

    @Column(name = "no_of_device_allowed")
    private Long noOfDeviceAllowed;
}
