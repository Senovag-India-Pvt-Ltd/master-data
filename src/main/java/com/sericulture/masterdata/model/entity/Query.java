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
public class Query extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "query_seq")
    @SequenceGenerator(name = "query_seq", sequenceName = "query_seq", allocationSize = 1)
    @Column(name = "query_id")
    private Long queryId;

    @Column(name = "query_name")
    private String queryName;
}
