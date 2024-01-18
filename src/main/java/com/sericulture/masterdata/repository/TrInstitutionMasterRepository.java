package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.TrCourseMaster;
import com.sericulture.masterdata.model.entity.TrInstitutionMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrInstitutionMasterRepository extends PagingAndSortingRepository<TrInstitutionMaster, Long> {
    public List<TrInstitutionMaster> findByTrInstitutionMasterName(String trInstitutionMasterName);

    public List<TrInstitutionMaster> findByTrInstitutionMasterNameAndTrInstitutionNameInKannada(String trInstitutionMasterName, String trInstitutionNameInKannada);

    public TrInstitutionMaster findByTrInstitutionMasterNameAndActive(String trInstitutionMasterName,boolean isActive);

    public Page<TrInstitutionMaster> findByActiveOrderByTrInstitutionMasterNameAsc(boolean isActive, final Pageable pageable);

    public TrInstitutionMaster save(TrInstitutionMaster trInstitutionMaster);

    public TrInstitutionMaster findByTrInstitutionMasterIdAndActive(long id, boolean isActive);

    public TrInstitutionMaster findByTrInstitutionMasterIdAndActiveIn(@Param("trInstitutionMasterId") long trInstitutionMasterId, @Param("active") Set<Boolean> active);

    public List<TrInstitutionMaster> findByActiveOrderByTrInstitutionMasterNameAsc(boolean isActive);

}
