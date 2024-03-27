package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.ScSubSchemeDetailsDTO;
import com.sericulture.masterdata.model.entity.ScSubSchemeDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface ScSubSchemeDetailsRepository extends PagingAndSortingRepository<ScSubSchemeDetails, Long> {
    public Page<ScSubSchemeDetails> findByActiveOrderByScSubSchemeDetailsIdAsc(boolean isActive, final Pageable pageable);

    List<ScSubSchemeDetails> findByScSchemeDetailsId(long scSchemeDetailsId);

    List<ScSubSchemeDetails> findByScSchemeDetailsIdAndScSubSchemeDetailsIdIsNot( long scSchemeDetailsId, long ScSubSchemeDetailsId);


    public ScSubSchemeDetails save(ScSubSchemeDetails scSubSchemeDetails);

    public ScSubSchemeDetails findByScSubSchemeDetailsIdAndActive(long id, boolean isActive);

    public ScSubSchemeDetails findByScSubSchemeDetailsIdAndActiveIn(@Param("scSubSchemeDetailsId") long scSubSchemeDetailsId, @Param("active") Set<Boolean> active);

    public List<ScSubSchemeDetails> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.ScSubSchemeDetailsDTO(" +
            " scSubSchemeDetails.scSubSchemeDetailsId," +
            " scSubSchemeDetails.scSchemeDetailsId," +
            " scSubSchemeDetails.subSchemeName," +
            " scSubSchemeDetails.subSchemeNameInKannada," +
            " scSubSchemeDetails.subSchemeType," +
            " scSubSchemeDetails.subSchemeStartDate," +
            " scSubSchemeDetails.subSchemeEndDate," +
            " scSchemeDetails.schemeName" +

            ") \n" +
            "from ScSubSchemeDetails scSubSchemeDetails\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on scSubSchemeDetails.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "where scSubSchemeDetails.active = :isActive " +
            "ORDER BY scSubSchemeDetails.scSubSchemeDetailsId ASC"
    )
    Page<ScSubSchemeDetailsDTO> getByActiveOrderByScSubSchemeDetailsIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScSubSchemeDetailsDTO(" +
            " scSubSchemeDetails.scSubSchemeDetailsId," +
            " scSubSchemeDetails.scSchemeDetailsId," +
            " scSubSchemeDetails.subSchemeName," +
            " scSubSchemeDetails.subSchemeNameInKannada," +
            " scSubSchemeDetails.subSchemeType," +
            " scSubSchemeDetails.subSchemeStartDate," +
            " scSubSchemeDetails.subSchemeEndDate," +
            " scSchemeDetails.schemeName" +
            ") \n" +
            "from ScSubSchemeDetails scSubSchemeDetails\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on scSubSchemeDetails.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "where scSubSchemeDetails.active = :isActive AND scSubSchemeDetails.scSubSchemeDetailsId = :id "
    )
    public ScSubSchemeDetailsDTO getByScSubSchemeDetailsIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.ScSubSchemeDetailsDTO(" +
            " scSubSchemeDetails.scSubSchemeDetailsId," +
            " scSubSchemeDetails.scSchemeDetailsId," +
            " scSubSchemeDetails.subSchemeName," +
            " scSubSchemeDetails.subSchemeNameInKannada," +
            " scSubSchemeDetails.subSchemeType," +
            " scSubSchemeDetails.subSchemeStartDate," +
            " scSubSchemeDetails.subSchemeEndDate," +
            " scSchemeDetails.schemeName" +
            ") \n" +
            "from ScSubSchemeDetails scSubSchemeDetails\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on scSubSchemeDetails.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "where scSubSchemeDetails.active = :isActive AND " +
            "(:joinColumn = 'scSchemeDetails.schemeName' AND scSchemeDetails.schemeName LIKE :searchText) "
    )
    public Page<ScSubSchemeDetailsDTO> getSortedScSubSchemeDetails(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}

