package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.ScHeadAccountDTO;
import com.sericulture.masterdata.model.entity.*;
import com.sericulture.masterdata.model.entity.ScHeadAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScHeadAccountRepository extends PagingAndSortingRepository<ScHeadAccount, Long> {
    public List<ScHeadAccount> findByScHeadAccountName(String scHeadAccountName);

    public List<ScHeadAccount> findByScSchemeDetailsIdAndActiveOrderByScHeadAccountNameAsc(long scSchemeDetailsId, boolean isActive);

    public List<ScHeadAccount> findByScHeadAccountNameAndScHeadAccountIdIsNot(String scHeadAccountName , long scHeadAccountId);

//    public List<ScHeadAccount> findByScProgramIdAndActiveOrderByScHeadAccountName(long scProgramId, boolean isActive);
    public ScHeadAccount findByScHeadAccountNameAndActive(String scHeadAccountName,boolean isActive);

    public Page<ScHeadAccount> findByActiveOrderByScHeadAccountNameAsc(boolean isActive, final Pageable pageable);

    public ScHeadAccount save(ScHeadAccount scHeadAccount);

    public ScHeadAccount findByScHeadAccountIdAndActive(long id, boolean isActive);

    public ScHeadAccount findByScHeadAccountIdAndActiveIn(@Param("scHeadAccountId") long scHeadAccountId, @Param("active") Set<Boolean> active);
    public List<ScHeadAccount> findByActive(boolean isActive);
    
    List<ScHeadAccount> findByScHeadAccountId( long scHeadAccountId);


    @Query("select new com.sericulture.masterdata.model.dto.ScHeadAccountDTO(" +
            " scHeadAccount.scHeadAccountId," +
            " scHeadAccount.scHeadAccountName," +
            " scHeadAccount.scHeadAccountNameInKannada," +
            " scHeadAccount.scSchemeDetailsId," +
            " scSchemeDetails.schemeName" +
            ") \n" +
            "from ScHeadAccount scHeadAccount\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on scHeadAccount.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "where scHeadAccount.active = :isActive " +
            "ORDER BY scHeadAccount.scHeadAccountId ASC"
    )
    Page<ScHeadAccountDTO> getByActiveOrderByScHeadAccountIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScHeadAccountDTO(" +
            " scHeadAccount.scHeadAccountId," +
            " scHeadAccount.scHeadAccountName," +
            " scHeadAccount.scHeadAccountNameInKannada," +
            " scHeadAccount.scSchemeDetailsId," +
            " scSchemeDetails.schemeName" +
            ") \n" +
            "from ScHeadAccount scHeadAccount\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on scHeadAccount.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "where scHeadAccount.active = :isActive AND scHeadAccount.scHeadAccountId = :id "
    )
    public ScHeadAccountDTO getByScHeadAccountIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.ScHeadAccountDTO(" +
            " scHeadAccount.scHeadAccountId," +
            " scHeadAccount.scHeadAccountName," +
            " scHeadAccount.scHeadAccountNameInKannada," +
            " scHeadAccount.scSchemeDetailsId," +
            " scSchemeDetails.schemeName" +
            ") \n" +
            "from ScHeadAccount scHeadAccount\n" +
            "left join ScSchemeDetails scSchemeDetails\n" +
            "on scHeadAccount.scSchemeDetailsId = scSchemeDetails.scSchemeDetailsId " +
            "where scHeadAccount.active = :isActive AND " +
            "(:joinColumn = 'scHeadAccount.scHeadAccountName' AND scHeadAccount.scHeadAccountName LIKE :searchText) OR " +
            "(:joinColumn = 'scSchemeDetails.schemeName' AND scSchemeDetails.schemeName LIKE :searchText)"
    )
    public Page<ScHeadAccountDTO> getSortedScHeadAccount(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}
