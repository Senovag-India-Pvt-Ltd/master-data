package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.ScSubSchemeMappingDTO;
import com.sericulture.masterdata.model.entity.ScSubSchemeMapping;
import com.sericulture.masterdata.model.entity.ScSubSchemeMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScSubSchemeMappingRepository extends PagingAndSortingRepository<ScSubSchemeMapping,Long> {
    public Page<ScSubSchemeMapping> findByActiveOrderByScSubSchemeMappingIdAsc(boolean isActive, final Pageable pageable);

    List<ScSubSchemeMapping> findByScSchemeDetailsIdAndScSubSchemeDetailsId(long scSchemeDetailsId, long scSubSchemeDetailsId);

    List<ScSubSchemeMapping> findByScSchemeDetailsIdAndScSubSchemeDetailsIdAndScSubSchemeMappingIdIsNot(long scSchemeDetailsId, long scSubSchemeDetailsId, long scSubSchemeMappingId);

//    List<ScSubSchemeMapping> findByScSchemeDetailsIdAndAndScSubSchemeDetailsIdAndScSubSchemeMappingIdIsNot(long scSchemeDetailsId,long scSubSchemeDetailsId, long scSubSchemeMappingId);

    public List<ScSubSchemeMapping> findByScSchemeDetailsIdAndScSubSchemeDetailsIdAndActive(long scSchemeDetailsId,long scSubSchemeDetailsId, boolean isActive);

    public ScSubSchemeMapping save(ScSubSchemeMapping scSubSchemeMapping);

    public ScSubSchemeMapping findByScSubSchemeMappingIdAndActive(long id, boolean isActive);

    public ScSubSchemeMapping findByScSubSchemeMappingIdAndActiveIn(@Param("scSubSchemeMappingId") long scSubSchemeMappingId, @Param("active") Set<Boolean> active);

    public List<ScSubSchemeMapping> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.ScSubSchemeMappingDTO(" +
            " scSubSchemeMapping.scSubSchemeMappingId," +
            " scSubSchemeMapping.scSchemeDetailsId," +
            " scSubSchemeMapping.scSubSchemeDetailsId," +
            " scSchemeDetails.schemeName," +
            " scSubSchemeDetails.subSchemeName" +
            ") \n" +
            "from ScSubSchemeMapping scSubSchemeMapping\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on scSubSchemeMapping.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "left join ScSubSchemeDetails scSubSchemeDetails\n" +
            "on scSubSchemeMapping.scSubSchemeDetailsId = scSubSchemeDetails.scSubSchemeDetailsId " +
            "where scSubSchemeMapping.active = :isActive " +
            "ORDER BY scSubSchemeMapping.scSubSchemeMappingId ASC"
    )
    Page<ScSubSchemeMappingDTO> getByActiveOrderByScSubSchemeMappingIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScSubSchemeMappingDTO(" +
            " scSubSchemeMapping.scSubSchemeMappingId," +
            " scSubSchemeMapping.scSchemeDetailsId," +
            " scSubSchemeMapping.scSubSchemeDetailsId," +
            " scSchemeDetails.schemeName," +
            " scSubSchemeDetails.subSchemeName" +
            ") \n" +
            "from ScSubSchemeMapping scSubSchemeMapping\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on scSubSchemeMapping.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "left join ScSubSchemeDetails scSubSchemeDetails\n" +
            "on scSubSchemeMapping.scSubSchemeDetailsId = scSubSchemeDetails.scSubSchemeDetailsId " +
            "where scSubSchemeMapping.active = :isActive AND scSubSchemeMapping.scSubSchemeMappingId = :id "
    )
    public ScSubSchemeMappingDTO getByScSubSchemeMappingIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.ScSubSchemeMappingDTO(" +
            " scSubSchemeMapping.scSubSchemeMappingId," +
            " scSubSchemeMapping.scSchemeDetailsId," +
            " scSubSchemeMapping.scSubSchemeDetailsId," +
            " scSchemeDetails.schemeName," +
            " scSubSchemeDetails.subSchemeName" +
            ") \n" +
            "from ScSubSchemeMapping scSubSchemeMapping\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on scSubSchemeMapping.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "left join ScSubSchemeDetails scSubSchemeDetails\n" +
            "on scSubSchemeMapping.scSubSchemeDetailsId = scSubSchemeDetails.scSubSchemeDetailsId " +
            "where scSubSchemeMapping.active = :isActive AND " +
            "(:joinColumn = 'scSchemeDetails.schemeName' AND scSchemeDetails.schemeName LIKE :searchText) OR " +
            "(:joinColumn = 'scSubSchemeDetails.subSchemeName' AND scSubSchemeDetails.subSchemeName LIKE :searchText)"
    )
    public Page<ScSubSchemeMappingDTO> getSortedScSubSchemeMapping(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);


}
