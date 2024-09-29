package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.CropStatus;
import com.sericulture.masterdata.model.entity.DiseaseStatus;
import com.sericulture.masterdata.model.entity.DiseaseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DiseaseStatusRepository extends PagingAndSortingRepository<DiseaseStatus, Long> {
    public List<DiseaseStatus> findByName(String name);

    public List<DiseaseStatus> findByNameAndDiseaseStatusIdIsNot(String name, long diseaseStatusId);

    public Page<DiseaseStatus> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public DiseaseStatus save(DiseaseStatus diseaseStatus);

    public DiseaseStatus findByDiseaseStatusIdAndActive(long id, boolean isActive);

    public DiseaseStatus findByDiseaseStatusIdAndActiveIn(@Param("diseaseStatusId") long diseaseStatusId, @Param("active") Set<Boolean> active);

    public List<DiseaseStatus> findByActiveOrderByNameAsc(boolean isActive);
}
