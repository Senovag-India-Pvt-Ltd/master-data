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
public class SilkExchange extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "silk_exchange_seq")
    @SequenceGenerator(name = "silk_exchange_seq", sequenceName = "silk_exchange_seq", allocationSize = 1)
    @Column(name = "silk_exchange_id")
    private Long silkExchangeId;


    @Size(min = 2, max = 250, message = "Silk Exchange name should be more than 1 characters.")
    @Column(name = "name")
    private String silkExchangeName;

    @Column(name = "name_in_kannada")
    private String silkExchNameInKannada;
}
