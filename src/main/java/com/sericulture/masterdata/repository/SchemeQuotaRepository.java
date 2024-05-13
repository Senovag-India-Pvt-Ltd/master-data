package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.SchemeQuotaDTO;
import com.sericulture.masterdata.model.entity.SchemeQuota;
import com.sericulture.masterdata.model.entity.SchemeQuota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SchemeQuotaRepository extends PagingAndSortingRepository<SchemeQuota, Long> {
    public Page<SchemeQuota> findByActiveOrderBySchemeQuotaIdAsc(boolean isActive, final Pageable pageable);

    List<SchemeQuota> findBySchemeQuotaName(String schemeQuotaName);

    List<SchemeQuota> findBySchemeQuotaNameAndSchemeQuotaIdIsNot( String schemeQuotaName, long schemeQuotaId);

    public List<SchemeQuota> findByScSchemeDetailsIdAndActiveOrderBySchemeQuotaNameAsc(long scSchemeDetailsId, boolean isActive);

    public SchemeQuota save(SchemeQuota schemeQuota);

    public SchemeQuota findBySchemeQuotaIdAndActive(long id, boolean isActive);

    public SchemeQuota findBySchemeQuotaIdAndActiveIn(@Param("schemeQuotaId") long schemeQuotaId, @Param("active") Set<Boolean> active);

    public List<SchemeQuota> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.SchemeQuotaDTO(" +
            " schemeQuota.schemeQuotaId," +
            " schemeQuota.scSchemeDetailsId," +
            " schemeQuota.schemeQuotaName," +
            " schemeQuota.schemeQuotaType," +
            " schemeQuota.schemeQuotaCode," +
            " schemeQuota.schemeQuotaPaymentType," +
            " schemeQuota.dbtCode," +
            " scSchemeDetails.schemeName" +

            ") \n" +
            "from SchemeQuota schemeQuota\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on schemeQuota.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "where schemeQuota.active = :isActive " +
            "ORDER BY schemeQuota.schemeQuotaId ASC"
    )
    Page<SchemeQuotaDTO> getByActiveOrderBySchemeQuotaIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.SchemeQuotaDTO(" +
            " schemeQuota.schemeQuotaId," +
            " schemeQuota.scSchemeDetailsId," +
            " schemeQuota.schemeQuotaName," +
            " schemeQuota.schemeQuotaType," +
            " schemeQuota.schemeQuotaCode," +
            " schemeQuota.schemeQuotaPaymentType," +
            " schemeQuota.dbtCode," +
            " scSchemeDetails.schemeName" +
            ") \n" +
            "from SchemeQuota schemeQuota\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on schemeQuota.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "where schemeQuota.active = :isActive AND schemeQuota.schemeQuotaId = :id "
    )
    public SchemeQuotaDTO getBySchemeQuotaIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.SchemeQuotaDTO(" +
            " schemeQuota.schemeQuotaId," +
            " schemeQuota.scSchemeDetailsId," +
            " schemeQuota.schemeQuotaName," +
            " schemeQuota.schemeQuotaType," +
            " schemeQuota.schemeQuotaCode," +
            " schemeQuota.schemeQuotaPaymentType," +
            " schemeQuota.dbtCode," +
            " scSchemeDetails.schemeName" +
            ") \n" +
            "from SchemeQuota schemeQuota\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on schemeQuota.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "where schemeQuota.active = :isActive AND " +
            "(:joinColumn = 'scSchemeDetails.schemeName' AND scSchemeDetails.schemeName LIKE :searchText)"

    )
    public Page<SchemeQuotaDTO> getSortedSchemeQuota(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}
