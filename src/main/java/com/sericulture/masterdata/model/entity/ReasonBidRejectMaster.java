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
public class ReasonBidRejectMaster extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reason_bid_reject_seq")
    @SequenceGenerator(name = "reason_bid_reject_seq", sequenceName = "reason_bid_reject_seq", allocationSize = 1)
    @Column(name = "reason_bid_reject_id")
    private Long reasonBidRejectId;


    @Size(min = 2, max = 250, message = "Reason of bid rejection name should be more than 1 characters.")
    @Column(name = "reason_bid_reject_name", unique = true)
    private String reasonBidRejectName;
}
