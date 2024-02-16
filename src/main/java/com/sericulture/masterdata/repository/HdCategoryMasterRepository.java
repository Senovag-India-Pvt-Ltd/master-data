package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.HdCategoryMasterDTO;
import com.sericulture.masterdata.model.dto.RaceMasterDTO;
import com.sericulture.masterdata.model.dto.TrScheduleDTO;
import com.sericulture.masterdata.model.entity.HdCategoryMaster;
import com.sericulture.masterdata.model.entity.HdFeatureMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface HdCategoryMasterRepository extends PagingAndSortingRepository<HdCategoryMaster, Long> {
    public List<HdCategoryMaster> findByHdCategoryName(String hdCategoryName);

    public List<HdCategoryMaster> findByHdCategoryNameAndHdCategoryIdIsNot(String hdCategoryName, long hdCategoryId);


    public HdCategoryMaster findByHdCategoryNameAndActive(String hdCategoryName, boolean isActive);

    public Page<HdCategoryMaster> findByActiveOrderByHdCategoryIdAsc(boolean isActive, final Pageable pageable);

    public List<HdCategoryMaster> findByHdBoardCategoryIdAndActive(int hdBoardCategoryId, boolean isActive);

    public HdCategoryMaster save(HdCategoryMaster hdCategoryMaster);

    List<HdCategoryMaster> findByHdCategoryNameAndHdBoardCategoryId(String hdCategoryName, long hdBoardCategoryId);

    public HdCategoryMaster findByHdCategoryIdAndActive(long id, boolean isActive);

    public HdCategoryMaster findByHdCategoryIdAndActiveIn(@Param("hdCategoryId") long hdCategoryId, @Param("active") Set<Boolean> active);

    public List<HdCategoryMaster> findByActiveOrderByHdCategoryNameAsc(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.HdCategoryMasterDTO(" +
            " hdCategoryMaster.hdCategoryId," +
            " hdCategoryMaster.hdBoardCategoryId," +
            " hdCategoryMaster.hdCategoryName," +
            " hdBoardCategoryMaster.hdBoardCategoryName" +
            ") \n" +
            "from HdCategoryMaster hdCategoryMaster\n" +
            "left join HdBoardCategoryMaster hdBoardCategoryMaster\n" +
            "on hdCategoryMaster.hdBoardCategoryId = hdBoardCategoryMaster.hdBoardCategoryId " +
            "where hdCategoryMaster.active = :isActive AND hdCategoryMaster.hdCategoryId = :id"
    )
    public HdCategoryMasterDTO getByHdCategoryIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.HdCategoryMasterDTO(" +
            " hdCategoryMaster.hdCategoryId," +
            " hdCategoryMaster.hdBoardCategoryId," +
            " hdCategoryMaster.hdCategoryName," +
            " hdBoardCategoryMaster.hdBoardCategoryName" +
            ") \n" +
            "from HdCategoryMaster hdCategoryMaster\n" +
            "left join HdBoardCategoryMaster hdBoardCategoryMaster\n" +
            "on hdCategoryMaster.hdBoardCategoryId = hdBoardCategoryMaster.hdBoardCategoryId " +
            "where hdCategoryMaster.active = :isActive " +
            "ORDER BY hdCategoryMaster.hdCategoryName ASC"
    )
    Page<HdCategoryMasterDTO> getByActiveOrderByHdCategoryIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.HdCategoryMasterDTO(" +
            " hdCategoryMaster.hdCategoryId," +
            " hdCategoryMaster.hdBoardCategoryId," +
            " hdCategoryMaster.hdCategoryName," +
            " hdBoardCategoryMaster.hdBoardCategoryName" +
            ") \n" +
            "from HdCategoryMaster hdCategoryMaster\n" +
            "left join HdBoardCategoryMaster hdBoardCategoryMaster\n" +
            "on hdCategoryMaster.hdBoardCategoryId = hdBoardCategoryMaster.hdBoardCategoryId " +
            "where hdCategoryMaster.active = :isActive AND " +
            "(:joinColumn = 'hdCategoryMaster.hdCategoryName' AND hdCategoryMaster.hdCategoryName LIKE :searchText) OR " +
            "(:joinColumn = 'hdBoardCategoryMaster.hdBoardCategoryName' AND hdBoardCategoryMaster.hdBoardCategoryName LIKE :searchText)"
    )
    public Page<HdCategoryMasterDTO> getSortedHdCategoryMasters(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

}
