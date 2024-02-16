package com.sericulture.masterdata.repository;


import com.sericulture.masterdata.model.entity.TrModeMaster;
import com.sericulture.masterdata.model.entity.TrOffice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrOfficeRepository extends PagingAndSortingRepository<TrOffice, Long> {

    public List<TrOffice> findByTrOfficeName(String trOfficeName);

    public List<TrOffice> findByTrOfficeNameAndTrOfficeIdIsNot(String trOfficeName, long trOfficeId);


    public TrOffice findByTrOfficeNameAndActive(String trOfficeName,boolean isActive);

    public Page<TrOffice> findByActiveOrderByTrOfficeNameAsc(boolean isActive, final Pageable pageable);

    public TrOffice save(TrOffice trOffice);

    public TrOffice findByTrOfficeIdAndActive(long id, boolean isActive);

    public TrOffice findByTrOfficeIdAndActiveIn(@Param("trOfficeId") long trOfficeId, @Param("active") Set<Boolean> active);

    public List<TrOffice> findByActiveOrderByTrOfficeNameAsc(boolean isActive);
}
