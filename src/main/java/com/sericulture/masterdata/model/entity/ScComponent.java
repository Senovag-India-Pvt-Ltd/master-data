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
public class ScComponent extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_component_seq")
    @SequenceGenerator(name = "sc_component_seq", sequenceName = "sc_component_seq", allocationSize = 1)
    @Column(name = "sc_component_id")
    private Long scComponentId;


    @Size(min = 2, max = 250, message = "Sc Component name should be more than 1 characters.")
    @Column(name = "sc_component_name", unique = true)
    private String scComponentName;
}
