package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.HobliDTO;
import com.sericulture.masterdata.model.dto.TalukDTO;
import com.sericulture.masterdata.model.entity.Hobli;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HobliRepository extends PagingAndSortingRepository<Hobli, Long> {
    public List<Hobli> findByHobliNameAndHobliNameInKannada(String hobliName,String hobliNameInKannada);

    public List<Hobli> findByHobliName(String hobliName);

    public List<Hobli> findByHobliNameAndTalukId(String hobliName, long talukId);

    public Hobli findByHobliNameAndTalukIdAndActive(String hobliName, long talukId, boolean isActive);

    public Hobli findByHobliNameAndActive(String hobliName,boolean isActive);

    public Page<Hobli> findByActiveOrderByHobliIdAsc(boolean isActive, final Pageable pageable);

    public Hobli save(Hobli hobli);

    public Hobli findByHobliIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.HobliDTO(" +
            " hobli.hobliId," +
            " hobli.hobliName," +
            " hobli.hobliNameInKannada," +
            " hobli.stateId," +
            " hobli.districtId," +
            " hobli.talukId," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName" +
            ") \n" +
            "from Hobli hobli\n" +
            "left join State state\n" +
            "on hobli.stateId = state.stateId " +
            "left join District district\n" +
            "on hobli.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on hobli.talukId = taluk.talukId " +
            "where hobli.active = :isActive " +
            "ORDER BY hobli.hobliId ASC"
    )
    Page<HobliDTO> getByActiveOrderByHobliIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.HobliDTO(" +
            " hobli.hobliId," +
            " hobli.hobliName," +
            " hobli.hobliNameInKannada," +
            " hobli.stateId," +
            " hobli.districtId," +
            " hobli.talukId," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName" +
            ") \n" +
            "from Hobli hobli\n" +
            "left join State state\n" +
            "on hobli.stateId = state.stateId " +
            "left join District district\n" +
            "on hobli.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on hobli.talukId = taluk.talukId " +
            "where hobli.active = :isActive AND hobli.hobliId = :id "
    )
    public HobliDTO getByHobliIdAndActive(long id, boolean isActive);

    public List<Hobli> findByTalukIdAndActive(long talukId, boolean isActive);


    public Hobli findByHobliIdAndActiveIn(@Param("hobliId") long hobliId, @Param("active") Set<Boolean> active);

    public List<Hobli> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.HobliDTO(" +
            " hobli.hobliId," +
            " hobli.hobliName," +
            " hobli.hobliNameInKannada," +
            " hobli.stateId," +
            " hobli.districtId," +
            " hobli.talukId," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName" +
            ") \n" +
            "from Hobli hobli\n" +
            "left join State state\n" +
            "on hobli.stateId = state.stateId " +
            "left join District district\n" +
            "on hobli.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on hobli.talukId = taluk.talukId " +
            "where hobli.active = :isActive AND " +
            "(:joinColumn = 'hobli.hobliName' AND hobli.hobliName LIKE :searchText) OR " +
            "(:joinColumn = 'state.stateName' AND state.stateName LIKE :searchText) OR " +
            "(:joinColumn = 'district.districtName' AND district.districtName LIKE :searchText) OR " +
            "(:joinColumn = 'taluk.talukName' AND taluk.talukName LIKE :searchText)"
    )
    public Page<HobliDTO> getSortedHoblis(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}