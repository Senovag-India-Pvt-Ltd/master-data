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
public class ScHeadAccountCategory extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sc_head_account_category_seq")
    @SequenceGenerator(name = "sc_head_account_category_seq", sequenceName = "sc_head_account_category_seq", allocationSize = 1)
    @Column(name = "sc_head_account_category_id")
    private Long scHeadAccountCategoryId;

    @Column(name = "sc_head_account_id")
    private Long scHeadAccountId;

    @Column(name = "sc_category_id")
    private Long scCategoryId;

}
