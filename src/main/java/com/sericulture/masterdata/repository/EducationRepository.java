package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.BinCounterMaster;
import com.sericulture.masterdata.model.entity.District;
import com.sericulture.masterdata.model.entity.Education;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EducationRepository extends PagingAndSortingRepository<Education, Long> {

    public Education findByCode(String code);

    public List<Education> findByNameAndEducationNameInKannada(String name, String educationNameInKannada);

    public List<Education> findByNameAndEducationNameInKannadaAndIdIsNot(String name, String educationNameInKannada,long id);


    public Education findByIdAndActive(long id, boolean isActive);

    public List<Education> findByNameAndActiveIn(@Param("name") String name, @Param("active") Set<Boolean> active);
    public Education findByIdAndActiveIn(@Param("id") long id, @Param("active") Set<Boolean> active);
    Page<Education> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public Education save(Education education);

    public List<Education> findByActive(boolean isActive);
}
