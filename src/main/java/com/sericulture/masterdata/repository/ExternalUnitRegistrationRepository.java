package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ExternalUnitRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ExternalUnitRegistrationRepository extends PagingAndSortingRepository<ExternalUnitRegistration, Long> {
    public Page<ExternalUnitRegistration> findByActiveOrderByExternalUnitRegistrationIdAsc(boolean isActive, final Pageable pageable);

    public ExternalUnitRegistration save(ExternalUnitRegistration farmerAddress);

    public ExternalUnitRegistration findByExternalUnitRegistrationIdAndActive(long id, boolean isActive);

    List<ExternalUnitRegistration> findByExternalUnitTypeIdAndActive(long externalUnitTypeId, boolean active);

    public ExternalUnitRegistration findByExternalUnitRegistrationIdAndActiveIn(@Param("externalUnitRegistrationId") long externalUnitRegistrationId, @Param("active") Set<Boolean> active);
}