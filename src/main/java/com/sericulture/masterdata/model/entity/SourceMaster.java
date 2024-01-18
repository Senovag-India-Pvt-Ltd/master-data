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
public class SourceMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "source_seq")
    @SequenceGenerator(name = "source_seq", sequenceName = "source_seq", allocationSize = 1)
    @Column(name = "source_id")
    private Long sourceMasterId;


    @Size(min = 2, max = 250, message = "Source name should be more than 1 characters.")
    @Column(name = "source_name", unique = true)
    private String sourceMasterName;

    @Column(name = "source_name_in_kannada")
    private String sourceNameInKannada;
}
