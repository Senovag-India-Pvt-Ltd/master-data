package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Agency;
import com.sericulture.masterdata.model.entity.Agency;
import com.sericulture.masterdata.model.entity.Designation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AgencyRepository extends PagingAndSortingRepository<Agency,Long> {
    public List<Agency> findByAgencyCode(String agencyCode);

    public List<Agency> findByAgencyCodeIsNot(String agencyCode);
    
    public Agency findByAgencyIdAndActive(long agencyId,boolean isActive);

    public Page<Agency> findByActiveOrderByAgencyIfscCodeAsc(boolean isActive, final Pageable pageable);

    public Agency save(Agency agency);


    public Agency findByAgencyIdAndActiveIn(@Param("agencyId") long AgencyId, @Param("active") Set<Boolean> active);

    public List<Agency> findByActive(boolean isActive);
}
