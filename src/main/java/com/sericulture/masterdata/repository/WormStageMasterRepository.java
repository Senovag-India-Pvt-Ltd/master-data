package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.RoofType;
import com.sericulture.masterdata.model.entity.WorkingInstitution;
import com.sericulture.masterdata.model.entity.WormStageMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface WormStageMasterRepository extends PagingAndSortingRepository<WormStageMaster,Long>  {

    public List<WormStageMaster> findByWormStageMasterNameAndWormStageMasterNameInKannada(String wormStageMasterName, String wormStageMasterNameInKannada);

    public List<WormStageMaster> findByActiveAndWormStageMasterNameAndWormStageMasterNameInKannada(boolean a,String wormStageMasterName,String wormStageMasterNameInKannada);

    public WormStageMaster findByWormStageMasterNameAndActive(String wormStageMasterName,boolean isActive);

    public Page<WormStageMaster> findByActiveOrderByWormStageMasterNameAsc(boolean isActive, final Pageable pageable);

    public WormStageMaster save(WormStageMaster wormStageMaster);

    public WormStageMaster findByWormStageMasterIdAndActive(long id, boolean isActive);

    public List<WormStageMaster> findByWormStageMasterNameAndWormStageMasterNameInKannadaAndWormStageMasterIdIsNot(String wormStageMasterName, String wormStageMasterNameInKannada, long wormStageMasterId);

    public WormStageMaster findByWormStageMasterIdAndActiveIn(@Param("wormStageMasterId") long wormStageMasterId, @Param("active") Set<Boolean> active);

    public List<WormStageMaster> findByActiveOrderByWormStageMasterNameAsc(boolean isActive);
}
