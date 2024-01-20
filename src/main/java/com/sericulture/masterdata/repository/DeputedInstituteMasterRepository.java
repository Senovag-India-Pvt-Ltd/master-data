package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.DeputedInstituteMaster;
import com.sericulture.masterdata.model.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DeputedInstituteMasterRepository extends PagingAndSortingRepository<DeputedInstituteMaster,Long> {
    public List<DeputedInstituteMaster> findByDeputedInstituteName(String deputedInstituteName);

    public DeputedInstituteMaster findByDeputedInstituteNameAndActive(String deputedInstituteName,boolean isActive);

    public Page<DeputedInstituteMaster> findByActiveOrderByDeputedInstituteNameAsc(boolean isActive, final Pageable pageable);

    public DeputedInstituteMaster save(DeputedInstituteMaster deputedInstituteMaster);

    public DeputedInstituteMaster findByDeputedInstituteIdAndActive(long id, boolean isActive);

    public DeputedInstituteMaster findByDeputedInstituteIdAndActiveIn(@Param("deputedInstituteId") long deputedInstituteId, @Param("active") Set<Boolean> active);

    public List<DeputedInstituteMaster> findByActiveOrderByDeputedInstituteNameAsc(boolean isActive);
}
