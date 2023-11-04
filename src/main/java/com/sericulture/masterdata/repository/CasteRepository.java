package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Caste;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CasteRepository extends PagingAndSortingRepository<Caste, Long> {

    public List<Caste> findByCode(String code);

    public List<Caste> findByTitle(String title);

    public Caste findByCodeAndActive(String code,boolean isActive);

    public Caste findByTitleAndActive(String title,boolean isActive);


    public Page<Caste> findByActiveOrderByIdAsc(boolean isActive, final Pageable pageable);

    public Caste save(Caste caste);

    public Caste findByIdAndActive(long id, boolean isActive);

    public Caste findByCodeAndTitle(String code,String title);

    public Caste findByIdAndActiveIn(@Param("id") long id, @Param("active") Set<Boolean> active);



}