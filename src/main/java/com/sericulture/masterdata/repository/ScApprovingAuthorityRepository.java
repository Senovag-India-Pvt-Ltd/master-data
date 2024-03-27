package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.ScApprovingAuthorityDTO;
import com.sericulture.masterdata.model.entity.ScApprovingAuthority;
import com.sericulture.masterdata.model.entity.ScApprovingAuthority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface ScApprovingAuthorityRepository extends PagingAndSortingRepository<ScApprovingAuthority,Long> {

    public Page<ScApprovingAuthority> findByActiveOrderByScApprovingAuthorityIdAsc(boolean isActive, final Pageable pageable);

    List<ScApprovingAuthority> findByRoleId(long roleId);

    List<ScApprovingAuthority> findByRoleIdAndScApprovingAuthorityIdIsNot(long roleId, long scApprovingAuthorityId);


    public ScApprovingAuthority save(ScApprovingAuthority scApprovingAuthority);

    public ScApprovingAuthority findByScApprovingAuthorityIdAndActive(long id, boolean isActive);

    public ScApprovingAuthority findByScApprovingAuthorityIdAndActiveIn(@Param("scApprovingAuthorityId") long scApprovingAuthorityId, @Param("active") Set<Boolean> active);

    public List<ScApprovingAuthority> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.ScApprovingAuthorityDTO(" +
            " scApprovingAuthority.scApprovingAuthorityId," +
            " scApprovingAuthority.minAmount," +
            " scApprovingAuthority.maxAmount," +
            " scApprovingAuthority.type," +
            " scApprovingAuthority.roleId," +
            " role.roleName" +
            ") \n" +
            "from ScApprovingAuthority scApprovingAuthority\n" +
            "left join role_master role\n" +
            "on scApprovingAuthority.roleId = role.roleId " +
            "where scApprovingAuthority.active = :isActive " +
            "ORDER BY scApprovingAuthority.scApprovingAuthorityId ASC"
    )
    Page<ScApprovingAuthorityDTO> getByActiveOrderByScApprovingAuthorityIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScApprovingAuthorityDTO(" +
            " scApprovingAuthority.scApprovingAuthorityId," +
            " scApprovingAuthority.minAmount," +
            " scApprovingAuthority.maxAmount," +
            " scApprovingAuthority.type," +
            " scApprovingAuthority.roleId," +
            " role.roleName" +
            ") \n" +
            "from ScApprovingAuthority scApprovingAuthority\n" +
            "left join role_master role\n" +
            "on scApprovingAuthority.roleId = role.roleId " +
            "where scApprovingAuthority.active = :isActive AND scApprovingAuthority.scApprovingAuthorityId = :id "
    )
    public ScApprovingAuthorityDTO getByScApprovingAuthorityIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.ScApprovingAuthorityDTO(" +
            " scApprovingAuthority.scApprovingAuthorityId," +
            " scApprovingAuthority.minAmount," +
            " scApprovingAuthority.maxAmount," +
            " scApprovingAuthority.type," +
            " scApprovingAuthority.roleId," +
            " role.roleName" +
            ") \n" +
            "from ScApprovingAuthority scApprovingAuthority\n" +
            "left join role_master role\n" +
            "on scApprovingAuthority.roleId = role.roleId " +
            "where scApprovingAuthority.active = :isActive AND " +
            "(:joinColumn = 'role.roleName' AND role.roleName LIKE :searchText) "

    )
    public Page<ScApprovingAuthorityDTO> getSortedScApprovingAuthority(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}


