package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScApprovingAuthority extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_approving_authority_seq")
    @SequenceGenerator(name = "sc_approving_authority_seq", sequenceName = "sc_approving_authority_seq", allocationSize = 1)
    @Column(name = "sc_approving_authority_id")
    private Long scApprovingAuthorityId;

    @Column(name = "min_amount")
    private BigDecimal minAmount;

    @Column(name = "max_amount")
    private BigDecimal maxAmount;

    @Column(name = "type")
    private Long type;

    @Column(name = "role_master_id")
    private Long roleId;

}
