package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ScSchemeDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface ScSchemeDetailsRepository extends PagingAndSortingRepository<ScSchemeDetails, Long> {
    public List<ScSchemeDetails> findBySchemeName(String schemeName);

    public List<ScSchemeDetails> findBySchemeNameAndSchemeNameInKannada(String schemeName,String schemeNameInKannada);

    public List<ScSchemeDetails> findByActiveAndSchemeNameAndSchemeNameInKannadaAndScSchemeDetailsIdIsNot(boolean a,String schemeName,String schemeNameInKannada,long scSchemeDetailsId);

    public ScSchemeDetails findBySchemeNameAndActive(String schemeName,boolean isActive);

    public Page<ScSchemeDetails> findByActiveOrderBySchemeNameAsc(boolean isActive, final Pageable pageable);

    public ScSchemeDetails save(ScSchemeDetails scSchemeDetails);

    public ScSchemeDetails findByScSchemeDetailsIdAndActive(long id, boolean isActive);

    public ScSchemeDetails findByScSchemeDetailsIdAndActiveIn(@Param("scSchemeDetailsId") long scSchemeDetailsId, @Param("active") Set<Boolean> active);

    public List<ScSchemeDetails> findByActive(boolean isActive);
}


