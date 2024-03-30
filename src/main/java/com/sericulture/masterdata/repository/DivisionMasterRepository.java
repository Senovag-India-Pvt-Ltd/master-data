package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.DivisionMaster;
import com.sericulture.masterdata.model.entity.DivisionMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface DivisionMasterRepository  extends PagingAndSortingRepository<DivisionMaster, Long> {

    public List<DivisionMaster> findByName(String name);

    public List<DivisionMaster> findByNameAndNameInKannada(String name,String nameInKannada);

    public List<DivisionMaster> findByActiveAndNameAndNameInKannada(boolean a,String name,String nameInKannada);

    public DivisionMaster findByNameAndActive(String name,boolean isActive);

    public Page<DivisionMaster> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public DivisionMaster save(DivisionMaster divisionMaster);

    public DivisionMaster findByDivisionMasterIdAndActive(long id, boolean isActive);

    public DivisionMaster findByDivisionMasterIdAndActiveIn(@Param("divisionMasterId") long divisionMasterId, @Param("active") Set<Boolean> active);

    public List<DivisionMaster> findByActive(boolean isActive);
}
