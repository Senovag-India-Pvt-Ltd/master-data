package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.TrProgramMaster;
import com.sericulture.masterdata.model.entity.TscMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TscMasterRepository extends PagingAndSortingRepository<TscMaster, Long> {

    public List<TscMaster> findByName(String name);

    public List<TscMaster> findByActiveAndNameAndNameInKannada(boolean isActive,String name,String nameInKannada);

    public List<TscMaster> findByActiveAndNameAndNameInKannadaAndTscMasterIdIsNot(boolean isActive,String name,String nameInKannada,long tscMasterId);

    public TscMaster findByNameAndActive(String name,boolean isActive);

    public Page<TscMaster> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public TscMaster save(TscMaster tscMaster);

    public TscMaster findByTscMasterIdAndActive(long id, boolean isActive);

    public TscMaster findByTscMasterIdAndActiveIn(@Param("tscMasterId") long tscMasterId, @Param("active") Set<Boolean> active);

    public List<TscMaster> findByActive(boolean isActive);
}
