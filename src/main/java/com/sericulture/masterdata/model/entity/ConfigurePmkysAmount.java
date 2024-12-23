package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
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
public class ConfigurePmkysAmount extends  BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configure_pmkys_amount_seq")
    @SequenceGenerator(name = "configure_pmkys_amount_seq", sequenceName = "configure_pmkys_amount_seq", allocationSize = 1)
    @Column(name = "configure_pmkys_amount_id")
    private Long configurePmkysAmountId;

    @Column(name = "spacing_id")
    private Long spacingId;

    @Column(name = "hectare_id")
    private Long hectareId;

    @Column(name = "amount")
    private Float amount;
}
