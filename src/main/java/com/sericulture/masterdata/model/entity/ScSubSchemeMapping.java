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
public class ScSubSchemeMapping extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_sub_scheme_mapping_seq")
    @SequenceGenerator(name = "sc_sub_scheme_mapping_seq", sequenceName = "sc_sub_scheme_mapping_seq", allocationSize = 1)
    @Column(name = "sc_sub_scheme_mapping_id")
    private Long scSubSchemeMappingId;


    @Column(name = "sc_scheme_details_id")
    private Long scSchemeDetailsId;

    @Column(name = "sc_sub_scheme_details_id")
    private Long scSubSchemeDetailsId;


}
