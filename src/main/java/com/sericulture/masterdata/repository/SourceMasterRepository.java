package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.RaceMaster;
import com.sericulture.masterdata.model.entity.SourceMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SourceMasterRepository extends PagingAndSortingRepository<SourceMaster, Long> {
    public List<SourceMaster> findBySourceMasterName(String sourceMasterName);

    public List<SourceMaster> findBySourceMasterNameAndSourceNameInKannada(String sourceMasterName,String sourceNameInKannada);

    public List<SourceMaster> findBySourceMasterNameAndSourceNameInKannadaAndSourceMasterIdIsNot(String sourceMasterName,String sourceNameInKannada, long sourceMasterId);


    public SourceMaster findBySourceMasterNameAndActive(String sourceMasterName,boolean isActive);

    public Page<SourceMaster> findByActiveOrderBySourceMasterNameAsc(boolean isActive, final Pageable pageable);

    public SourceMaster save(SourceMaster sourceMaster);

    public SourceMaster findBySourceMasterIdAndActive(long id, boolean isActive);

    public SourceMaster findBySourceMasterIdAndActiveIn(@Param("sourceMasterId") long sourceMasterId, @Param("active") Set<Boolean> active);

    public List<SourceMaster> findByActive(boolean isActive);

}
