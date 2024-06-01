package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.GenerationNumberMaster;
import com.sericulture.masterdata.model.entity.TrModeMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GenerationNumberMasterRepository extends PagingAndSortingRepository<GenerationNumberMaster, Long> {

//    public List<GenerationNumberMaster> findByGenerationNumberAndActive(String generationNumber);

    public List<GenerationNumberMaster> findByActiveAndGenerationNumberAndGenerationNumberIdIsNot(boolean isActive,String generationNumber,long generationNumberId);


    public List<GenerationNumberMaster> findByGenerationNumberAndActive(String generationNumber,boolean isActive);

    public Page<GenerationNumberMaster> findByActiveOrderByGenerationNumberAsc(boolean isActive, final Pageable pageable);

    public GenerationNumberMaster save(GenerationNumberMaster generationNumberMaster);

    public GenerationNumberMaster findByGenerationNumberIdAndActive(long id, boolean isActive);

    public GenerationNumberMaster findByGenerationNumberIdAndActiveIn(@Param("generationNumberId") long generationNumberId, @Param("active") Set<Boolean> active);

    public List<GenerationNumberMaster> findByActiveOrderByGenerationNumberAsc(boolean isActive);


}
