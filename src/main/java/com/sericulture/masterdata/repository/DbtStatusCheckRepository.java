package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.DbtStatusCheck;
import com.sericulture.masterdata.model.entity.DbtStatusCheck;
import com.sericulture.masterdata.model.entity.DocumentMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DbtStatusCheckRepository extends PagingAndSortingRepository<DbtStatusCheck, Long> {

    public List<DbtStatusCheck> findByUsername(String username);

    public List<DbtStatusCheck> findByUsernameAndDbtStatusCheckIdIsNot(String username, long dbtStatusCheckId);

    public Page<DbtStatusCheck> findByActiveOrderByDbtStatusCheckIdAsc(boolean isActive, final Pageable pageable);

    public DbtStatusCheck save(DbtStatusCheck dbtStatusCheck);

    public DbtStatusCheck findByDbtStatusCheckIdAndActive(long id, boolean isActive);

    public DbtStatusCheck findByDbtStatusCheckIdAndActiveIn(@Param("dbtStatusCheckId") long dbtStatusCheckId, @Param("active") Set<Boolean> active);

    public List<DbtStatusCheck> findByActiveOrderByUsernameAsc(boolean isActive);
   
}
