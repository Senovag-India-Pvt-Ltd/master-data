package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Taluk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TalukRepository extends PagingAndSortingRepository<Taluk, Long> {
    public List<Taluk> findByTalukName(String talukName);

    public List<Taluk> findByTalukNameAndDistrictId(String talukName, long districtId);

    public Taluk findByTalukNameAndActive(String talukName,boolean isActive);

    public Page<Taluk> findByActiveOrderByTalukIdAsc(boolean isActive, final Pageable pageable);

    public Taluk save(Taluk taluk);

    public Taluk findByTalukIdAndActive(long id, boolean isActive);

    public List<Taluk> findByTalukNameAndTalukId(String name, long talukId);

    public List<Taluk> findByDistrictIdAndActive(long districtId, boolean isActive);

   // public Page<Taluk> findByDistrictIdAndActive(long districtId, boolean isActive, final Pageable pageable);


    public Taluk findByTalukIdAndActiveIn(@Param("talukId") long talukId, @Param("active") Set<Boolean> active);
}