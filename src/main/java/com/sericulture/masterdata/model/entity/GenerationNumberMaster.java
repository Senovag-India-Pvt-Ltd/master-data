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
public class GenerationNumberMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generation_number_master_seq")
    @SequenceGenerator(name = "generation_number_master_seq", sequenceName = "generation_number_master_seq", allocationSize = 1)
    @Column(name = "generation_number_id")
    private Long generationNumberId;


//    @Size(min = 2, max = 250, message = "GenerationNumber name should be more than 1 characters.")
    @Column(name = "generation_number", unique = true)
    private String generationNumber;
}
