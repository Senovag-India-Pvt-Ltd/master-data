package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.TrCourseMaster;
import com.sericulture.masterdata.model.entity.TrGroupMaster;
import com.sericulture.masterdata.model.entity.TrInstitutionMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrGroupMasterRepository extends PagingAndSortingRepository<TrGroupMaster, Long> {
    public List<TrGroupMaster> findByTrGroupMasterName(String trGroupMasterName);

    public List<TrGroupMaster> findByTrGroupMasterNameAndTrGroupNameInKannada(String trGroupMasterName, String trGroupNameInKannada);

    public List<TrGroupMaster> findByTrGroupMasterNameAndTrGroupNameInKannadaAndTrGroupMasterIdIsNot(String trGroupMasterName, String trGroupNameInKannada,long trGroupMasterId);

    public TrGroupMaster findByTrGroupMasterNameAndActive(String trGroupMasterName,boolean isActive);

    public Page<TrGroupMaster> findByActiveOrderByTrGroupMasterNameAsc(boolean isActive, final Pageable pageable);

    public TrGroupMaster save(TrGroupMaster trGroupMaster);

    public TrGroupMaster findByTrGroupMasterIdAndActive(long id, boolean isActive);

    public TrGroupMaster findByTrGroupMasterIdAndActiveIn(@Param("trGroupMasterId") long trGroupMasterId, @Param("active") Set<Boolean> active);

    public List<TrGroupMaster> findByActiveOrderByTrGroupMasterNameAsc(boolean isActive);

}
