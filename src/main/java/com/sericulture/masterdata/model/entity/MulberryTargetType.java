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
public class MulberryTargetType extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mulberry_target_type_seq")
    @SequenceGenerator(name = "mulberry_target_type_seq", sequenceName = "mulberry_target_type_seq", allocationSize = 1)
    @Column(name = "mulberry_target_type_id")
    private Long mulberryTargetTypeId;


    @Size(min = 2, max = 250, message = "Mulberry source variety name should be more than 1 characters.")
    @Column(name = "mulberry_target_type_name", unique = true)
    private String mulberryTargetTypeName;

}
