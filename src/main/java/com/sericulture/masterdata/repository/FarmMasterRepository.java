package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.DisinfectantMaster;
import com.sericulture.masterdata.model.entity.FarmMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository

public interface FarmMasterRepository extends PagingAndSortingRepository<FarmMaster, Long> {

    public List<FarmMaster> findByFarmNameAndFarmNameInKannada(String farmName, String farmNameInKannada);

    public List<FarmMaster> findByFarmNameAndFarmNameInKannadaAndFarmIdIsNot(String farmName,String farmNameInKannada,long farmId);

    public FarmMaster findByFarmNameAndActive(String farmName,boolean isActive);

    public Page<FarmMaster> findByActiveOrderByFarmNameAsc(boolean isActive, final Pageable pageable);

    public FarmMaster save(FarmMaster farmMaster);

    public FarmMaster findByFarmIdAndActive(long id, boolean isActive);

    public FarmMaster findByFarmIdAndActiveIn(@Param("farmId") long farmId, @Param("active") Set<Boolean> active);

    public List<FarmMaster> findByActiveOrderByFarmNameAsc(boolean isActive);
}
