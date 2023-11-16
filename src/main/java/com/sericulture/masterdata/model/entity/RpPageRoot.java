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
public class RpPageRoot extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rp_page_root_seq")
    @SequenceGenerator(name = "rp_page_root_seq", sequenceName = "rp_page_root_seq", allocationSize = 1)
    @Column(name = "rp_page_root_id")
    private Long rpPageRootId;


    @Size(min = 2, max = 250, message = "Rp page root name should be more than 1 characters.")
    @Column(name = " rp_page_root_name", unique = true)
    private String rpPageRootName;
}
