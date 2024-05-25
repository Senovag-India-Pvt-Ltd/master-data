package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.ScVendorBankDTO;
import com.sericulture.masterdata.model.entity.ScVendorBank;
import com.sericulture.masterdata.model.entity.ScVendorBank;
import com.sericulture.masterdata.model.entity.ScVendorContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface ScVendorBankRepository extends PagingAndSortingRepository<ScVendorBank, Long> {
    public Page<ScVendorBank> findByActiveOrderByScVendorBankIdAsc(boolean isActive, final Pageable pageable);

    List<ScVendorBank> findByScVendorId( long scVendorId);

    List<ScVendorBank> findByScVendorIdAndScVendorBankIdIsNot( long scVendorId, long scVendorBankId);

    public List<ScVendorBank> findByScVendorIdAndActiveOrderByBankNameAsc(long scVendorId, boolean isActive);

    public ScVendorBank save(ScVendorBank scVendorBank);

    public ScVendorBank findByScVendorBankIdAndActive(long id, boolean isActive);

    public ScVendorBank findByScVendorBankIdAndActiveIn(@Param("scVendorBankId") long scVendorBankId, @Param("active") Set<Boolean> active);

    public List<ScVendorBank> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.ScVendorBankDTO(" +
            " scVendorBank.scVendorBankId," +
            " scVendorBank.bankName," +
            " scVendorBank.ifscCode," +
            " scVendorBank.branch," +
            " scVendorBank.accountNumber," +
            " scVendorBank.upi," +
            " scVendorBank.scVendorId," +
            " scVendor.name" +
            ") \n" +
            "from ScVendorBank scVendorBank\n" +
            "left join ScVendor scVendor\n" +
            "on scVendorBank.scVendorId = scVendor.scVendorId " +
            "where scVendorBank.active = :isActive " +
            "ORDER BY scVendorBank.scVendorBankId ASC"
    )
    Page<ScVendorBankDTO> getByActiveOrderByScVendorBankIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScVendorBankDTO(" +
            " scVendorBank.scVendorBankId," +
            " scVendorBank.bankName," +
            " scVendorBank.ifscCode," +
            " scVendorBank.branch," +
            " scVendorBank.accountNumber," +
            " scVendorBank.upi," +
            " scVendorBank.scVendorId," +
            " scVendor.name" +
            ") \n" +
            "from ScVendorBank scVendorBank\n" +
            "left join ScVendor scVendor\n" +
            "on scVendorBank.scVendorId = scVendor.scVendorId " +
            "where scVendorBank.active = :isActive AND scVendorBank.scVendorBankId = :id "
    )
    public ScVendorBankDTO getByScVendorBankIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.ScVendorBankDTO(" +
            " scVendorBank.scVendorBankId," +
            " scVendorBank.bankName," +
            " scVendorBank.ifscCode," +
            " scVendorBank.branch," +
            " scVendorBank.accountNumber," +
            " scVendorBank.upi," +
            " scVendorBank.scVendorId," +
            " scVendor.name" +
            ") \n" +
            "from ScVendorBank scVendorBank\n" +
            "left join ScVendor scVendor\n" +
            "on scVendorBank.scVendorId = scVendor.scVendorId " +
            "where scVendorBank.active = :isActive AND " +
            "(:joinColumn = 'scVendor.name' AND scVendor.name LIKE :searchText) OR " +
            "(:joinColumn = 'scVendorBank.bankName' AND scVendorBank.bankName LIKE :searchText)"
    )
    public Page<ScVendorBankDTO> getSortedScVendorBank(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
