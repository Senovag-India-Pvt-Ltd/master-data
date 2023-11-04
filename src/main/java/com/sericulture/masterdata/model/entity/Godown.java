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
public class Godown extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "godown_seq")
    @SequenceGenerator(name = "godown_seq", sequenceName = "godown_seq", allocationSize = 1)
    @Column(name = "godown_id")
    private Long godownId;


    @Size(min = 2, max = 250, message = "Godown name should be more than 1 characters.")
    @Column(name = "godown_name", unique = true)
    private String godownName;

    @Column(name = "market_master_id")
    private Long marketMasterId;
}