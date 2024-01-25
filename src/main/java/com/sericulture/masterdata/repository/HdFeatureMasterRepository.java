package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.HdFeatureMasterDTO;
import com.sericulture.masterdata.model.dto.RaceMasterDTO;
import com.sericulture.masterdata.model.entity.HdFeatureMaster;
import com.sericulture.masterdata.model.entity.RaceMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HdFeatureMasterRepository extends PagingAndSortingRepository<HdFeatureMaster, Long> {
    public List<HdFeatureMaster> findByHdFeatureName(String hdFeatureName);

    public HdFeatureMaster findByHdFeatureNameAndActive(String hdFeatureName,boolean isActive);

    public Page<HdFeatureMaster> findByActiveOrderByHdFeatureIdAsc(boolean isActive, final Pageable pageable);

    public List<HdFeatureMaster> findByHdModuleIdAndActive(int hdModuleId, boolean isActive);

    public HdFeatureMaster save(HdFeatureMaster hdFeatureMaster);

    List<HdFeatureMaster> findByHdFeatureNameAndHdModuleId(String hdFeatureName, long hdModuleId);

    public HdFeatureMaster findByHdFeatureIdAndActive(long id, boolean isActive);

    public HdFeatureMaster findByHdFeatureIdAndActiveIn(@Param("hdFeatureId") long hdFeatureId, @Param("active") Set<Boolean> active);

    public List<HdFeatureMaster> findByActiveOrderByHdFeatureNameAsc(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.HdFeatureMasterDTO(" +
            " hdFeatureMaster.hdFeatureId," +
            " hdFeatureMaster.hdModuleId," +
            " hdFeatureMaster.hdFeatureName," +
            " hdModuleMaster.hdModuleName" +
            ") \n" +
            "from HdFeatureMaster hdFeatureMaster\n" +
            "left join HdModuleMaster hdModuleMaster\n" +
            "on hdFeatureMaster.hdModuleId = hdModuleMaster.hdModuleId " +
            "where hdFeatureMaster.active = :isActive AND hdFeatureMaster.hdFeatureId = :id"
    )
    public HdFeatureMasterDTO getByHdFeatureIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.HdFeatureMasterDTO(" +
            " hdFeatureMaster.hdFeatureId," +
            " hdFeatureMaster.hdModuleId," +
            " hdFeatureMaster.hdFeatureName," +
            " hdModuleMaster.hdModuleName" +
            ") \n" +
            "from HdFeatureMaster hdFeatureMaster\n" +
            "left join HdModuleMaster hdModuleMaster\n" +
            "on hdFeatureMaster.hdModuleId = hdModuleMaster.hdModuleId " +
            "where hdFeatureMaster.active = :isActive " +
            "ORDER BY hdFeatureMaster.hdFeatureName ASC"
    )
    Page<HdFeatureMasterDTO> getByActiveOrderByHdFeatureIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.HdFeatureMasterDTO(" +
            " hdFeatureMaster.hdFeatureId," +
            " hdFeatureMaster.hdModuleId," +
            " hdFeatureMaster.hdFeatureName," +
            " hdModuleMaster.hdModuleName" +
            ") \n" +
            "from HdFeatureMaster hdFeatureMaster\n" +
            "left join HdModuleMaster hdModuleMaster\n" +
            "on hdFeatureMaster.hdModuleId = hdModuleMaster.hdModuleId " +
            "where hdFeatureMaster.active = :isActive AND " +
            "(:joinColumn = 'hdFeatureMaster.hdFeatureName' AND hdFeatureMaster.hdFeatureName LIKE :searchText) OR " +
            "(:joinColumn = 'hdModuleMaster.hdModuleName' AND hdModuleMaster.hdModuleName LIKE :searchText)"
    )
    public Page<HdFeatureMasterDTO> getSortedHdFeatureMasters(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
