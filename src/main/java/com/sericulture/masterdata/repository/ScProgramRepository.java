package com.sericulture.masterdata.repository;


import com.sericulture.masterdata.model.entity.ScProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScProgramRepository extends PagingAndSortingRepository<ScProgram, Long> {
    public List<ScProgram> findByScProgramName(String scProgramName);

    public ScProgram findByScProgramNameAndActive(String scProgramName,boolean isActive);

    public Page<ScProgram> findByActiveOrderByScProgramNameAsc(boolean isActive, final Pageable pageable);

    public ScProgram save(ScProgram scProgram);

    public ScProgram findByScProgramIdAndActive(long id, boolean isActive);

    public ScProgram findByScProgramIdAndActiveIn(@Param("scProgramId") long scProgramId, @Param("active") Set<Boolean> active);
    public List<ScProgram> findByActive(boolean isActive);
}
