package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "scheme_document")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SchemeDocumentMaster extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scheme_document_seq")
    @SequenceGenerator(name = "scheme_document_seq", sequenceName = "scheme_document_seq", allocationSize = 1)
    @Column(name = "scheme_document_id")
    private Long schemeDocumentId;

    @Column(name = "sc_scheme_details_id")
    private Integer scSchemeDetailsId;

    @Column(name = "sc_sub_scheme_details_id")
    private Integer scSubSchemeDetailsId;

    @Column(name = "document_id")
    private Integer documentId;

    @Column(name = "allow", columnDefinition = "TINYINT DEFAULT 1")
    private Boolean allow = true;
}
