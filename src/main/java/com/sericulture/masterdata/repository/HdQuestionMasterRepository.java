package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.HdQuestionMasterDTO;
import com.sericulture.masterdata.model.entity.HdQuestionMaster;
import com.sericulture.masterdata.model.entity.HdStatusMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface HdQuestionMasterRepository extends PagingAndSortingRepository<HdQuestionMaster, Long> {
    public List<HdQuestionMaster> findByHdQuestionName(String hdQuestionName);

    public HdQuestionMaster findByHdQuestionNameAndActive(String hdQuestionName, boolean isActive);

    public Page<HdQuestionMaster> findByActiveOrderByHdQuestionNameAsc(boolean isActive, final Pageable pageable);

    public HdQuestionMaster save(HdQuestionMaster hdQuestionMaster);

    public HdQuestionMaster findByHdQuestionIdAndActive(long id, boolean isActive);

    public HdQuestionMaster findByHdQuestionIdAndActiveIn(@Param("hdQuestionId") long hdQuestionId, @Param("active") Set<Boolean> active);

    public List<HdQuestionMaster> findByActiveOrderByHdQuestionNameAsc(boolean isActive);

//    @Query("SELECT NEW com.sericulture.masterdata.model.dto.HdQuestionMasterDTO(" +
//            " hdQuestionMaster.hdQuestionId," +
//            " hdQuestionMaster.hdQuestionName," +
//            " hdQuestionMaster.hdQuestionAnswerName," +
//            " hdQuestionMaster.hdFaqUploadPath" +
//            ") " +
//            "FROM HdQuestionMaster hdQuestionMaster " +
//            "WHERE (:joinColumn = 'hdQuestionMaster.hdQuestionName' AND hdQuestionMaster.hdQuestionName LIKE :searchText) OR " +
//            "(:joinColumn = 'hdQuestionMaster.hdQuestionAnswerName' AND hdQuestionMaster.hdQuestionAnswerName LIKE :searchText)"
//    )
//    public Page<HdQuestionMasterDTO> getSortedHdQuestions(
//            @Param("joinColumn") String joinColumn,
//            @Param("searchText") String searchText,
//            Pageable pageable
//    );
    @Query("SELECT NEW com.sericulture.masterdata.model.dto.HdQuestionMasterDTO(" +
            " hdQuestionMaster.hdQuestionId," +
            " hdQuestionMaster.hdQuestionName," +
            " hdQuestionMaster.hdQuestionAnswerName," +
            " hdQuestionMaster.hdFaqUploadPath" +
            ") " +
            "FROM HdQuestionMaster hdQuestionMaster " +
            "WHERE hdQuestionMaster.hdQuestionName LIKE :searchText OR hdQuestionMaster.hdQuestionAnswerName LIKE :searchText"
    )
    public Page<HdQuestionMasterDTO> getSortedHdQuestions(
            @Param("searchText") String searchText,
            Pageable pageable
    );


}


