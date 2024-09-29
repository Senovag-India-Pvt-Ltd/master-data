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
public class HectareMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hectare_master_seq")
    @SequenceGenerator(name = "hectare_master_seq", sequenceName = "hectare_master_seq", allocationSize = 1)
    @Column(name = "hectare_master_id")
    private Long hectareId;

    @Size(min = 2, max = 250, message = "Hd Status name should be more than 1 characters.")
    @Column(name = "hectare_master_name", unique = true)
    private String hectareName;
}
