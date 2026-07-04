package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "arm_calculation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArmCalculation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arm_calculation_seq")
    @SequenceGenerator(name = "arm_calculation_seq", sequenceName = "arm_calculation_seq", allocationSize = 1)
    @Column(name = "arm_calculation_id")
    private Long armCalculationId;

    @Column(name = "sc_category_id")
    private Long scCategoryId;

    @Column(name = "component_id")
    private Long componentId;

    @Column(name = "component_type_id")
    private Long componentTypeId;

    @Column(name = "equipment_name", length = 500)
    private String equipmentName;

    @Column(name = "quantity", precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(name = "unit_rate", precision = 18, scale = 2)
    private BigDecimal unitRate;

    @Column(name = "unit_cost", precision = 18, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "central_percentage", precision = 5, scale = 2)
    private BigDecimal centralPercentage;

    @Column(name = "state_percentage", precision = 5, scale = 2)
    private BigDecimal statePercentage;

    @Column(name = "arm_ends", length = 50)
    private String armEnds;
}
