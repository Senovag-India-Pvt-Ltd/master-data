package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.HdCategoryMasterDTO;
import com.sericulture.masterdata.model.dto.HdSubCategoryMasterDTO;
import com.sericulture.masterdata.model.entity.HdCategoryMaster;
import com.sericulture.masterdata.model.entity.HdSubCategoryMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface HdSubCategoryMasterRepository extends PagingAndSortingRepository<HdSubCategoryMaster, Long> {

    public List<HdSubCategoryMaster> findByHdSubCategoryName(String hdSubCategoryName);

    public HdSubCategoryMaster findByHdSubCategoryNameAndActive(String hdSubCategoryName, boolean isActive);

    public Page<HdSubCategoryMaster> findByActiveOrderByHdSubCategoryNameAsc(boolean isActive, final Pageable pageable);

    public HdSubCategoryMaster save(HdSubCategoryMaster hdSubCategoryMaster);

    public HdSubCategoryMaster findByHdSubCategoryIdAndActive(long id, boolean isActive);

    public HdSubCategoryMaster findByHdSubCategoryIdAndActiveIn(@Param("hdSubCategoryId") long hdSubCategoryId, @Param("active") Set<Boolean> active);

    public List<HdSubCategoryMaster> findByActiveOrderByHdSubCategoryNameAsc(boolean isActive);
    @Query("select new com.sericulture.masterdata.model.dto.HdSubCategoryMasterDTO(" +
            " hdSubCategoryMaster.hdSubCategoryId," +
            " hdSubCategoryMaster.hdCategoryId," +
            " hdSubCategoryMaster.hdBoardCategoryId," +
            " hdSubCategoryMaster.hdSubCategoryName," +
            " hdCategoryMaster.hdCategoryName," +
            " hdBoardCategoryMaster.hdBoardCategoryName" +
            ") \n" +
            "from HdSubCategoryMaster hdSubCategoryMaster\n" +
            "left join HdCategoryMaster hdCategoryMaster\n" +
            "on hdSubCategoryMaster.hdCategoryId = hdCategoryMaster.hdCategoryId " +
            "left join HdBoardCategoryMaster hdBoardCategoryMaster\n" +
            "on hdSubCategoryMaster.hdBoardCategoryId = hdBoardCategoryMaster.hdBoardCategoryId " +
            "where hdSubCategoryMaster.active = :isActive AND hdSubCategoryMaster.hdSubCategoryId = :id"
    )
    public HdSubCategoryMasterDTO getByHdSubCategoryIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.HdSubCategoryMasterDTO(" +
            " hdSubCategoryMaster.hdSubCategoryId," +
            " hdSubCategoryMaster.hdCategoryId," +
            " hdSubCategoryMaster.hdBoardCategoryId," +
            " hdSubCategoryMaster.hdSubCategoryName," +
            " hdCategoryMaster.hdCategoryName," +
            " hdBoardCategoryMaster.hdBoardCategoryName" +
            ") \n" +
            "from HdSubCategoryMaster hdSubCategoryMaster\n" +
            "left join HdCategoryMaster hdCategoryMaster\n" +
            "on hdSubCategoryMaster.hdCategoryId = hdCategoryMaster.hdCategoryId " +
            "left join HdBoardCategoryMaster hdBoardCategoryMaster\n" +
            "on hdSubCategoryMaster.hdBoardCategoryId = hdBoardCategoryMaster.hdBoardCategoryId " +
            "where hdSubCategoryMaster.active = :isActive " +
            "ORDER BY hdSubCategoryMaster.hdSubCategoryName ASC"
    )
    Page<HdSubCategoryMasterDTO> getByActiveOrderByHdSubCategoryIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.HdSubCategoryMasterDTO(" +
            " hdSubCategoryMaster.hdSubCategoryId," +
            " hdSubCategoryMaster.hdCategoryId," +
            " hdSubCategoryMaster.hdBoardCategoryId," +
            " hdSubCategoryMaster.hdSubCategoryName," +
            " hdCategoryMaster.hdCategoryName," +
            " hdBoardCategoryMaster.hdBoardCategoryName" +
            ") \n" +
            "from HdSubCategoryMaster hdSubCategoryMaster\n" +
            "left join HdCategoryMaster hdCategoryMaster\n" +
            "on hdSubCategoryMaster.hdCategoryId = hdCategoryMaster.hdCategoryId " +
            "left join HdBoardCategoryMaster hdBoardCategoryMaster\n" +
            "on hdSubCategoryMaster.hdBoardCategoryId = hdBoardCategoryMaster.hdBoardCategoryId " +
            "where hdCategoryMaster.active = :isActive AND " +
            "(:joinColumn = 'hdSubCategoryMaster.hdSubCategoryName' AND hdSubCategoryMaster.hdSubCategoryName LIKE :searchText) OR " +
            "(:joinColumn = 'hdCategoryMaster.hdCategoryName' AND hdCategoryMaster.hdCategoryName LIKE :searchText)"
    )
    public Page<HdSubCategoryMasterDTO> getSortedHdSubCategoryMasters(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}



