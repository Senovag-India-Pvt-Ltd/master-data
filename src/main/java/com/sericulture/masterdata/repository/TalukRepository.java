package com.sericulture.masterdata.repository;
import com.sericulture.masterdata.model.dto.DistrictDTO;
import com.sericulture.masterdata.model.dto.TalukDTO;
import com.sericulture.masterdata.model.entity.Taluk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TalukRepository extends PagingAndSortingRepository<Taluk, Long> {
    public List<Taluk> findByTalukNameAndTalukNameInKannada(String talukName,String talukNameInKannada);

    public List<Taluk> findByActiveAndTalukNameAndTalukNameInKannadaAndTalukIdIsNot(boolean a,String talukName,String talukNameInKannada,long talukId);

    Taluk findByTalukNameAndDistrictIdAndActive(String talukName, long districtId, boolean a);

    Taluk findByLgTaluk(String lgTaluk);

    public List<Taluk> findByTalukNameAndDistrictId(String talukName, long districtId);

    public List<Taluk> findByTalukName(String talukName);

    public Taluk findByTalukNameAndActive(String talukName,boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TalukDTO(" +
            " taluk.talukId," +
            " taluk.talukName," +
            " taluk.talukNameInKannada," +
            " taluk.stateId," +
            " taluk.districtId," +
            " taluk.lgTaluk," +
            " taluk.talukCode," +
            " state.stateName," +
            " district.districtName" +
            ") \n" +
            "from Taluk taluk\n" +
            "left join State state\n" +
            "on taluk.stateId = state.stateId " +
            "left join District district\n" +
            "on taluk.districtId = district.districtId " +
            "where taluk.active = :isActive " +
            "ORDER BY taluk.talukName ASC"
    )
    Page<TalukDTO> getByActiveOrderByTalukIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TalukDTO(" +
            " taluk.talukId," +
            " taluk.talukName," +
            " taluk.talukNameInKannada," +
            " taluk.stateId," +
            " taluk.districtId," +
            " taluk.lgTaluk," +
            " taluk.talukCode," +
            " state.stateName," +
            " district.districtName" +
            ") \n" +
            "from Taluk taluk\n" +
            "left join State state\n" +
            "on taluk.stateId = state.stateId " +
            "left join District district\n" +
            "on taluk.districtId = district.districtId " +
            "where taluk.active = :isActive AND taluk.talukId = :id"
    )
    public TalukDTO getByTalukIdAndActive(long id, boolean isActive);


    public Page<Taluk> findByActiveOrderByTalukIdAsc(boolean isActive, final Pageable pageable);

    public Taluk save(Taluk taluk);

    public Taluk findByTalukIdAndActive(long id, boolean isActive);

    public List<Taluk> findByTalukNameAndTalukId(String name, long talukId);

    public List<Taluk> findByDistrictIdAndActiveOrderByTalukNameAsc(long districtId, boolean isActive);

   // public Page<Taluk> findByDistrictIdAndActive(long districtId, boolean isActive, final Pageable pageable);


    public Taluk findByTalukIdAndActiveIn(@Param("talukId") long talukId, @Param("active") Set<Boolean> active);

    public List<Taluk> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TalukDTO(" +
            " taluk.talukId," +
            " taluk.talukName," +
            " taluk.talukNameInKannada," +
            " taluk.stateId," +
            " taluk.districtId," +
            " taluk.lgTaluk," +
            " taluk.talukCode," +
            " state.stateName," +
            " district.districtName" +
            ") \n" +
            "from Taluk taluk\n" +
            "left join State state\n" +
            "on taluk.stateId = state.stateId " +
            "left join District district\n" +
            "on taluk.districtId = district.districtId " +
            "where taluk.active = :isActive AND " +
            "(:joinColumn = 'taluk.talukName' AND taluk.talukName LIKE :searchText) OR " +
            "(:joinColumn = 'district.districtName' AND district.districtName LIKE :searchText) OR " +
            "(:joinColumn = 'state.stateName' AND state.stateName LIKE :searchText)"
    )
    public Page<TalukDTO> getSortedTaluks(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}
