package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.DisinfectantMaster;
import com.sericulture.masterdata.model.entity.LineNameMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface LineNameMasterRepository extends PagingAndSortingRepository <LineNameMaster, Long> {

    public List<LineNameMaster> findByLineNameAndLineNameInKannada(String lineName, String lineNameInKannada);

    public List<LineNameMaster> findByLineNameAndLineNameInKannadaAndLineNameIdIsNot(String lineName,String lineNameInKannada,long lineNameId);

    public LineNameMaster findByLineNameAndActive(String lineName,boolean isActive);

    public Page<LineNameMaster> findByActiveOrderByLineNameAsc(boolean isActive, final Pageable pageable);

    public LineNameMaster save(LineNameMaster lineNameMaster);

    public LineNameMaster findByLineNameIdAndActive(long id, boolean isActive);

    public LineNameMaster findByLineNameIdAndActiveIn(@Param("lineNameId") long lineNameId, @Param("active") Set<Boolean> active);

    public List<LineNameMaster> findByActiveOrderByLineNameAsc(boolean isActive);
}
