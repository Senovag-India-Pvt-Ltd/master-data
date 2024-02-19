package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.GrainageMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GrainageMasterRepository extends PagingAndSortingRepository<GrainageMaster, Long> {

    public List<GrainageMaster> findByGrainageMasterNameAndGrainageMasterNameInKannada(String grainageMasterName, String grainageMasterNameInKannada);

    public List<GrainageMaster> findByGrainageMasterNameAndGrainageMasterNameInKannadaAndGrainageMasterIdIsNot(String grainageMasterName, String grainageMasterNameInKannada, long grainageMasterId);

    public GrainageMaster findByGrainageMasterNameAndActive(String grainageMasterName, boolean isActive);

    public Page<GrainageMaster> findByActiveOrderByGrainageMasterNameAsc(boolean isActive, final Pageable pageable);

    public GrainageMaster save(GrainageMaster grainageMaster);

    public GrainageMaster findByGrainageMasterIdAndActive(long id, boolean isActive);

    public GrainageMaster findByGrainageMasterIdAndActiveIn(@Param("grainageMasterId") long grainageMasterId, @Param("active") Set<Boolean> active);

    public List<GrainageMaster> findByActiveOrderByGrainageMasterNameAsc(boolean isActive);
}


