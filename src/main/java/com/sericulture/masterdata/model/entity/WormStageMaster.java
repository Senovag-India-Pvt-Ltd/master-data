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
public class WormStageMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "worm_stage_master_seq")
    @SequenceGenerator(name = "worm_stage_master_seq", sequenceName = "worm_stage_master_seq", allocationSize = 1)
    @Column(name = "worm_stage_master_id")
    private Long wormStageMasterId;

    @Size(min = 2, max = 250, message = "Worm Stage name should be more than 1 characters.")
    @Column(name = "name", unique = true)
    private String wormStageMasterName;

    @Column(name = "name_in_kannada")
    private String wormStageMasterNameInKannada;
}
