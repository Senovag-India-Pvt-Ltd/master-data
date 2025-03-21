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
@Table(name = "dbt_status_check")
@NoArgsConstructor
@Getter
@Setter
public class DbtStatusCheck extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dbt_status_check_seq")
    @SequenceGenerator(name = "dbt_status_check_seq", sequenceName = "dbt_status_check_seq", allocationSize = 1)
    @Column(name = "dbt_status_check_id")
    private Long dbtStatusCheckId;

    @Column(name = "dept_code")
    private Long deptCode;

    @Column(name = "scheme_id")
    private Long schemeId;

    @Column(name = "component_type_id")
    private Long componentTypeId;

    @Column(name = "component_id")
    private Long componentId;

    @Column(name = "sub_component_id")
    private Long subComponentId;

    @Column(name = "dbt_scheme")
    private Long dbtScheme;


    @Column(name = "user_name", unique = true)
    private String username;

    @Column(name = "password" , unique = true)
    private String password;
}
