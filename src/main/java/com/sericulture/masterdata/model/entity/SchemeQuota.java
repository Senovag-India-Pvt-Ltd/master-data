package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SchemeQuota extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scheme_quota_seq")
    @SequenceGenerator(name = "scheme_quota_seq", sequenceName = "scheme_quota_seq", allocationSize = 1)
    @Column(name = "scheme_quota_id")
    private Long schemeQuotaId;

    @Column(name = "sc_scheme_details_id")
    private Long scSchemeDetailsId;


    @Column(name = "scheme_quota_name")
    private String schemeQuotaName;


    @Column(name = "scheme_quota_type")
    private String schemeQuotaType;

    @Column(name = "scheme_quota_code")
    private String schemeQuotaCode;

    @Column(name = "scheme_quota_payment_type")
    private String schemeQuotaPaymentType;

}
