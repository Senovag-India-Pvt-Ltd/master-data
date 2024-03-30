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
public class ScHeadAccount extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_head_account_seq")
    @SequenceGenerator(name = "sc_head_account_seq", sequenceName = "sc_head_account_seq", allocationSize = 1)
    @Column(name = "sc_head_account_id")
    private Long scHeadAccountId;


    @Size(min = 2, max = 250, message = "Sc Head Account name should be more than 1 characters.")
    @Column(name = "sc_head_account_name", unique = true)
    private String scHeadAccountName;

    @Size(min = 2, max = 250, message = "Head Account name should be more than 1 characters.")
    @Column(name = "name_in_kannada", unique = true)
    private String scHeadAccountNameInKannada;

    @Column(name = "sc_scheme_details_id")
    private Long scSchemeDetailsId;
}
