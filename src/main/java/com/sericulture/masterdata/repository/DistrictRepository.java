package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DistrictRepository extends PagingAndSortingRepository<District, Long> {
    public List<District> findByDistrictName(String districtName);

    public List<District> findByDistrictNameAndStateId(String districtName, long stateId);

    public District findByDistrictNameAndActive(String districtName,boolean isActive);

    public Page<District> findByActiveOrderByDistrictIdAsc(boolean isActive, final Pageable pageable);

    public District save(District district);

    public District findByDistrictIdAndActive(long id, boolean isActive);

    public District findByDistrictIdAndActiveIn(@Param("districtId") long districtId, @Param("active") Set<Boolean> active);
}