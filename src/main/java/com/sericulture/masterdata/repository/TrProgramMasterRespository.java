package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.TrCourseMaster;
import com.sericulture.masterdata.model.entity.TrProgramMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface TrProgramMasterRespository extends PagingAndSortingRepository<TrProgramMaster, Long> {
    public List<TrProgramMaster> findByTrProgramMasterName(String trProgramMasterName);

    public List<TrProgramMaster> findByTrProgramMasterNameAndTrProgramNameInKannada(String trProgramMasterName,String trProgramNameInKannada);

    public List<TrProgramMaster> findByActiveAndTrProgramMasterNameAndTrProgramNameInKannada(boolean a,String trProgramMasterName,String trProgramNameInKannada);

    public TrProgramMaster findByTrProgramMasterNameAndActive(String trProgramMasterName,boolean isActive);

    public Page<TrProgramMaster> findByActiveOrderByTrProgramMasterNameAsc(boolean isActive, final Pageable pageable);

    public TrProgramMaster save(TrProgramMaster trProgramMaster);

    public TrProgramMaster findByTrProgramMasterIdAndActive(long id, boolean isActive);

    public TrProgramMaster findByTrProgramMasterIdAndActiveIn(@Param("trProgramMasterId") long trProgramMasterId, @Param("active") Set<Boolean> active);

    public List<TrProgramMaster> findByActive(boolean isActive);
}
