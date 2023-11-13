package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.HobliDTO;
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
    public List<Taluk> findByTalukName(String talukName);

    public List<Taluk> findByTalukNameAndDistrictId(String talukName, long districtId);

    public Taluk findByTalukNameAndActive(String talukName,boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TalukDTO(" +
            " taluk.talukId," +
            " taluk.talukName," +
            " taluk.stateId," +
            " taluk.districtId," +
            " state.stateName," +
            " district.districtName" +
            ") \n" +
            "from Taluk taluk\n" +
            "left join State state\n" +
            "on taluk.stateId = state.stateId " +
            "left join District district\n" +
            "on taluk.districtId = district.districtId " +
            "where taluk.active = :isActive " +
            "ORDER BY taluk.talukId ASC"
    )
    Page<TalukDTO> getByActiveOrderByTalukIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);


    public Page<Taluk> findByActiveOrderByTalukIdAsc(boolean isActive, final Pageable pageable);

    public Taluk save(Taluk taluk);

    public Taluk findByTalukIdAndActive(long id, boolean isActive);

    public List<Taluk> findByTalukNameAndTalukId(String name, long talukId);

    public List<Taluk> findByDistrictIdAndActive(long districtId, boolean isActive);

   // public Page<Taluk> findByDistrictIdAndActive(long districtId, boolean isActive, final Pageable pageable);


    public Taluk findByTalukIdAndActiveIn(@Param("talukId") long talukId, @Param("active") Set<Boolean> active);

    public List<Taluk> findByActive(boolean isActive);
}
