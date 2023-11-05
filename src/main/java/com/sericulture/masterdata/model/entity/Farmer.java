package com.sericulture.masterdata.model.entity;

import jakarta.persistence.*;
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
public class Farmer extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FARMER_SEQ")
    @SequenceGenerator(name = "FARMER_SEQ", sequenceName = "FARMER_SEQ", allocationSize = 1)
    @Column(name = "FARMER_ID")
    private Long farmerId;

    @Column(name = "FARMER_NUMBER")
    private String farmerNumber;

    @Column(name = "FRUITS_ID")
    private String fruitsId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "DOB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;

    @Column(name = "GENDER_ID")
    private Long genderId;

    @Column(name = "CASTE_ID")
    private Long casteId;

    @Column(name = "DIFFERENTLY_ABLED", columnDefinition = "TINYINT")
    private Boolean differentlyAbled;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "AADHAAR_NUMBER")
    private String aadhaarNumber;

    @Column(name = "EPIC_NUMBER")
    private String epicNumber;

    @Column(name = "RATION_CARD_NUMBER")
    private String rationCardNumber;

    @Column(name = "TOTAL_LAND_HOLDING")
    private String totalLandHolding;

    @Column(name = "PASSBOOK_NUMBER")
    private String passbookNumber;

    @Column(name = "LANDHOLDING_CATEGORY_ID")
    private Long landHoldingCategoryId;

    @Column(name = "EDUCATION_ID")
    private Long educationId;

    @Column(name = "REPRESENTATIVE_ID")
    private Long representativeId;

    @Column(name = "KHAZANE_RECIPIENT_ID")
    private String khazaneRecipientId;

    @Column(name = "PHOTO_PATH")
    private String photoPath;
}