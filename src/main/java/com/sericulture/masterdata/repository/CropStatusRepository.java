package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.CropStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CropStatusRepository  extends PagingAndSortingRepository<CropStatus, Long> {

    public List<CropStatus> findByName( String name);

    public List<CropStatus> findByNameAndCropStatusIdIsNotAndActive( String name,long cropStatusId, boolean active);

    public CropStatus findByNameAndActive(String name,boolean isActive);



    public Page<CropStatus> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public CropStatus save(CropStatus cropStatus);

    public CropStatus findByCropStatusIdAndActive(long cropStatusId, boolean isActive);


    public CropStatus findByCropStatusIdAndActiveIn(@Param("id") long id, @Param("active") Set<Boolean> active);

    public List<CropStatus> findByActive(boolean isActive);
}
