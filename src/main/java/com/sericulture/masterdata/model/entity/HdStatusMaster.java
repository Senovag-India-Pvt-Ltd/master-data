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
public class HdStatusMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hd_status_master_seq")
    @SequenceGenerator(name = "hd_status_master_seq", sequenceName = "hd_status_master_seq", allocationSize = 1)
    @Column(name = "hd_status_id")
    private Long hdStatusId;

    @Size(min = 2, max = 250, message = "Hd Status name should be more than 1 characters.")
    @Column(name = "hd_status_name", unique = true)
    private String hdStatusName;
}
