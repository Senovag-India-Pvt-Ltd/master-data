package com.sericulture.masterdata.model.entity;

import com.fasterxml.jackson.databind.DatabindException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginHistory extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "login_history_seq")
    @SequenceGenerator(name = "login_history_seq", sequenceName = "login_history_seq", allocationSize = 1)
    @Column(name = "login_history_id")
    private Long loginHistoryId;

    @Column(name = "user_master_id", unique = true)
    private Long userMasterId;

    @Column(name = "username")
    private String username;

    @Column(name = "login_time")
    private LocalDateTime loginTime;

    @Column(name = "logout_time")
    private LocalDateTime logoutTime;

    @Column(name = "login_status")
    private Long loginStatus;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "error_description")
    private String errorDescription;
}
