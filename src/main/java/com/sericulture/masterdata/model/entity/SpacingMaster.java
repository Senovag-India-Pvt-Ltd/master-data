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
public class SpacingMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spacing_master_seq")
    @SequenceGenerator(name = "spacing_master_seq", sequenceName = "spacing_master_seq", allocationSize = 1)
    @Column(name = "spacing_master_id")
    private Long spacingId;

    @Size(min = 2, max = 250, message = "Hd Status name should be more than 1 characters.")
    @Column(name = "spacing_master_name", unique = true)
    private String spacingName;
}
