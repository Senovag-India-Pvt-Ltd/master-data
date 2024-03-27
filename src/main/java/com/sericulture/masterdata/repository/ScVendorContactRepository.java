package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.ScVendorContactDTO;
import com.sericulture.masterdata.model.entity.ScVendorContact;
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

public interface ScVendorContactRepository extends PagingAndSortingRepository<ScVendorContact, Long> {
    public Page<ScVendorContact> findByActiveOrderByScVendorContactIdAsc(boolean isActive, final Pageable pageable);

    List<ScVendorContact> findByScVendorId( long scVendorId);

    List<ScVendorContact> findByScVendorIdAndScVendorContactIdIsNot(long scVendorId, long scVendorContactId);


    public ScVendorContact save(ScVendorContact scVendorContact);

    public ScVendorContact findByScVendorContactIdAndActive(long id, boolean isActive);

    public ScVendorContact findByScVendorContactIdAndActiveIn(@Param("scVendorContactId") long scVendorContactId, @Param("active") Set<Boolean> active);

    public List<ScVendorContact> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.ScVendorContactDTO(" +
            " scVendorContact.scVendorContactId," +
            " scVendorContact.vendorAddress," +
            " scVendorContact.phone," +
            " scVendorContact.email," +
            " scVendorContact.scVendorId," +
            " scVendor.name" +
            ") \n" +
            "from ScVendorContact scVendorContact\n" +
            "left join ScVendor scVendor\n" +
            "on scVendorContact.scVendorId = scVendor.scVendorId " +
            "where scVendorContact.active = :isActive " +
            "ORDER BY scVendorContact.scVendorContactId ASC"
    )
    Page<ScVendorContactDTO> getByActiveOrderByScVendorContactIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScVendorContactDTO(" +
            " scVendorContact.scVendorContactId," +
            " scVendorContact.vendorAddress," +
            " scVendorContact.phone," +
            " scVendorContact.email," +
            " scVendorContact.scVendorId," +
            " scVendor.name" +
            ") \n" +
            "from ScVendorContact scVendorContact\n" +
            "left join ScVendor scVendor\n" +
            "on scVendorContact.scVendorId = scVendor.scVendorId " +
            "where scVendorContact.active = :isActive AND scVendorContact.scVendorContactId = :id "
    )
    public ScVendorContactDTO getByScVendorContactIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.ScVendorContactDTO(" +
            " scVendorContact.scVendorContactId," +
            " scVendorContact.vendorAddress," +
            " scVendorContact.phone," +
            " scVendorContact.email," +
            " scVendorContact.scVendorId," +
            " scVendor.name" +
            ") \n" +
            "from ScVendorContact scVendorContact\n" +
            "left join ScVendor scVendor\n" +
            "on scVendorContact.scVendorId = scVendor.scVendorId " +
            "where scVendorContact.active = :isActive AND " +
            "(:joinColumn = 'scVendor.name' AND scVendor.name LIKE :searchText) OR " +
            "(:joinColumn = 'scVendorContact.phone' AND scVendorContact.phone LIKE :searchText)"
    )
    public Page<ScVendorContactDTO> getSortedScVendorContact(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}
