package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.HectareMaster;
import com.sericulture.masterdata.model.entity.HdSubCategoryMaster;
import com.sericulture.masterdata.model.entity.HectareMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HectareMasterRepository extends PagingAndSortingRepository<HectareMaster, Long> {

    public List<HectareMaster> findByHectareName(String hectareName);

    public List<HectareMaster> findByHectareNameAndHectareIdIsNot(String hectareName,long hectareId);


    public Page<HectareMaster> findByActiveOrderByHectareNameAsc(boolean isActive, final Pageable pageable);

    public HectareMaster save(HectareMaster hectareMaster);

    public HectareMaster findByHectareIdAndActive(long id, boolean isActive);

    public HectareMaster findByHectareIdAndActiveIn(@Param("hectareId") long hectareId, @Param("active") Set<Boolean> active);

    public List<HectareMaster> findByActiveOrderByHectareNameAsc(boolean isActive);
}
