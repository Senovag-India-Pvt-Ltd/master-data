package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Subsidy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SubsidyRepository extends PagingAndSortingRepository<Subsidy, Long> {
    public List<Subsidy> findBySubsidyNameAndSubsidyNameInKannada(String subsidyName,String subsidyNameInKannada);

    public List<Subsidy> findBySubsidyNameAndSubsidyNameInKannadaAndSubsidyIdIsNot(String subsidyName,String subsidyNameInKannada,long subsidyId);

    public Subsidy findBySubsidyNameAndActive(String subsidyName,boolean isActive);

    public Page<Subsidy> findByActiveOrderBySubsidyNameAsc(boolean isActive, final Pageable pageable);

    public Subsidy save(Subsidy subsidy);

    public Subsidy findBySubsidyIdAndActive(long id, boolean isActive);

    public Subsidy findBySubsidyIdAndActiveIn(@Param("subsidyId") long subsidyId, @Param("active") Set<Boolean> active);

    public List<Subsidy> findByActive(boolean isActive);
}
