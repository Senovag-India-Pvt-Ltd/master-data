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
public class RpPagePermission extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RP_PAGE_PERMISSION_SEQ")
    @SequenceGenerator(name = "RP_PAGE_PERMISSION_SEQ", sequenceName = "RP_PAGE_PERMISSION_SEQ", allocationSize = 1)
    @Column(name = "RP_PAGE_PERMISSION_ID")
    private Long rpPagePermissionId;

    @Column(name = "root")
    private Long root;

    @Column(name = "parent")
    private Long parent;

    @Column(name = "page_name")
    private String pageName;

    @Column(name = "route")
    private String route;

    @Column(name = "is_page", columnDefinition = "TINYINT")
    private Boolean isPage;

    @Column(name = "map_code")
    private String mapCode;
}