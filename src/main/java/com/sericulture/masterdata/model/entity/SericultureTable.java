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
@Table(name = "sericulture_table")
public class SericultureTable extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sericulture_table_seq")
    @SequenceGenerator(name = "sericulture_table_seq", sequenceName = "sericulture_table_seq", allocationSize = 1)
    @Column(name = "sericulture_table_id")
    private Long sericultureTableId;

    @Column(name = "step_id")
    private Integer stepId;

    @Column(name = "days_count")
    private Integer daysCount;

    @Column(name = "scheme_id")
    private Long schemeId;

    @Column(name = "sub_scheme_id")
    private Long subSchemeId;
}
