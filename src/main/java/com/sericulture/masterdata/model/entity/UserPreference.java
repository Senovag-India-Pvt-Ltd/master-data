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
public class UserPreference extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_preference_seq")
    @SequenceGenerator(name = "user_preference_seq", sequenceName = "user_preference_seq", allocationSize = 1)
    @Column(name = "user_preference_id")
    private Long userPreferenceId;

    @Column(name = "user_master_id", unique = true)
    private Long userMasterId;

    @Column(name = "godown_master_id")
    private Long godownId;

}
