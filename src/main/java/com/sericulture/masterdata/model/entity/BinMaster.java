package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity(name="bin_master")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BinMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bin_master_seq")
    @SequenceGenerator(name = "bin_master_seq", sequenceName = "bin_master_seq", allocationSize = 1)
    @Column(name = "bin_master_id")
    private Long binMasterId;

    @Column(name = "bin_counter_master_id")
    private Long binCounterMasterId;

    @Column(name = "type")
    private Long type;

    @Size(min = 2, max = 250, message = "BinNumber name should be more than 1 characters.")
    @Column(name = "bin_number", unique = true)
    private String binNumber;

    @Column(name = "status")
    private Long status;
}
