package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.MarketMasterDTO;
import com.sericulture.masterdata.model.dto.TrainingDeputationTrackerDTO;
import com.sericulture.masterdata.model.entity.MarketMaster;
import com.sericulture.masterdata.model.entity.TrainingDeputationTracker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrainingDeputationTrackerRepository extends PagingAndSortingRepository<TrainingDeputationTracker,Long> {

//    public List<TrainingDeputationTracker> findByMarketMasterNameAndMarketNameInKannada(String marketMasterName, String marketNameInKannada);
//
//    public TrainingDeputationTracker findByMarketMasterNameAndActive(String marketMasterName,boolean isActive);

    public Page<TrainingDeputationTracker> findByActiveOrderByTrainingDeputationIdAsc(boolean isActive, final Pageable pageable);

    public TrainingDeputationTracker save(TrainingDeputationTracker trainingDeputationTracker);

    public TrainingDeputationTracker findByTrainingDeputationIdAndActive(long trainingDeputationId, boolean isActive);

    public TrainingDeputationTracker findByTrainingDeputationIdAndActiveIn(@Param("trainingDeputationId") long trainingDeputationId, @Param("active") Set<Boolean> active);

    public List<TrainingDeputationTracker> findByActiveOrderByOfficialNameAsc(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TrainingDeputationTrackerDTO(" +
            " trainingDeputationTracker.trainingDeputationId," +
            " trainingDeputationTracker.officialName," +
            " trainingDeputationTracker.designationId," +
            " trainingDeputationTracker.officialAddress," +
            " trainingDeputationTracker.mobileNumber," +
            " trainingDeputationTracker.deputedInstituteId," +
            " trainingDeputationTracker.deputedFromDate," +
            " trainingDeputationTracker.deputedToDate," +
            " trainingDeputationTracker.trProgramMasterId," +
            " trainingDeputationTracker.trCourseMasterId," +
            " trainingDeputationTracker.deputedAttended," +
            " trainingDeputationTracker.deputedRemarks," +
            " designation.name," +
            " deputedInstituteMaster.deputedInstituteName," +
            " trProgramMaster.trProgramMasterName," +
            " trCourseMaster.trCourseMasterName" +
            ") \n" +
            "from TrainingDeputationTracker trainingDeputationTracker\n" +
            "left join Designation designation\n" +
            "on trainingDeputationTracker.designationId = designation.designationId " +
            "left join DeputedInstituteMaster deputedInstituteMaster\n" +
            "on trainingDeputationTracker.deputedInstituteId = deputedInstituteMaster.deputedInstituteId " +
            "left join TrProgramMaster trProgramMaster\n" +
            "on trainingDeputationTracker.trProgramMasterId = trProgramMaster.trProgramMasterId " +
            "left join TrCourseMaster trCourseMaster\n" +
            "on trainingDeputationTracker.trCourseMasterId = trCourseMaster.trCourseMasterId " +
            "where trainingDeputationTracker.active = :isActive " +
            "ORDER BY trainingDeputationTracker.officialName ASC"
    )
    Page<TrainingDeputationTrackerDTO> getByActiveOrderByTrainingDeputationIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TrainingDeputationTrackerDTO(" +
            " trainingDeputationTracker.trainingDeputationId," +
            " trainingDeputationTracker.officialName," +
            " trainingDeputationTracker.designationId," +
            " trainingDeputationTracker.officialAddress," +
            " trainingDeputationTracker.mobileNumber," +
            " trainingDeputationTracker.deputedInstituteId," +
            " trainingDeputationTracker.deputedFromDate," +
            " trainingDeputationTracker.deputedToDate," +
            " trainingDeputationTracker.trProgramMasterId," +
            " trainingDeputationTracker.trCourseMasterId," +
            " trainingDeputationTracker.deputedAttended," +
            " trainingDeputationTracker.deputedRemarks," +
            " designation.name," +
            " deputedInstituteMaster.deputedInstituteName," +
            " trProgramMaster.trProgramMasterName," +
            " trCourseMaster.trCourseMasterName" +
            ") \n" +
            "from TrainingDeputationTracker trainingDeputationTracker\n" +
            "left join Designation designation\n" +
            "on trainingDeputationTracker.designationId = designation.designationId " +
            "left join DeputedInstituteMaster deputedInstituteMaster\n" +
            "on trainingDeputationTracker.deputedInstituteId = deputedInstituteMaster.deputedInstituteId " +
            "left join TrProgramMaster trProgramMaster\n" +
            "on trainingDeputationTracker.trProgramMasterId = trProgramMaster.trProgramMasterId " +
            "left join TrCourseMaster trCourseMaster\n" +
            "on trainingDeputationTracker.trCourseMasterId = trCourseMaster.trCourseMasterId " +
            "where trainingDeputationTracker.active = :isActive AND trainingDeputationTracker.trainingDeputationId = :id "
    )
    public TrainingDeputationTrackerDTO getByTrainingDeputationIdAndActive(@Param("id") long id, @Param("isActive") boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TrainingDeputationTrackerDTO(" +
            " trainingDeputationTracker.trainingDeputationId," +
            " trainingDeputationTracker.officialName," +
            " trainingDeputationTracker.designationId," +
            " trainingDeputationTracker.officialAddress," +
            " trainingDeputationTracker.mobileNumber," +
            " trainingDeputationTracker.deputedInstituteId," +
            " trainingDeputationTracker.deputedFromDate," +
            " trainingDeputationTracker.deputedToDate," +
            " trainingDeputationTracker.trProgramMasterId," +
            " trainingDeputationTracker.trCourseMasterId," +
            " trainingDeputationTracker.deputedAttended," +
            " trainingDeputationTracker.deputedRemarks," +
            " designation.name," +
            " deputedInstituteMaster.deputedInstituteName," +
            " trProgramMaster.trProgramMasterName," +
            " trCourseMaster.trCourseMasterName" +
            ") \n" +
            "from TrainingDeputationTracker trainingDeputationTracker\n" +
            "left join Designation designation\n" +
            "on trainingDeputationTracker.designationId = designation.designationId " +
            "left join DeputedInstituteMaster deputedInstituteMaster\n" +
            "on trainingDeputationTracker.deputedInstituteId = deputedInstituteMaster.deputedInstituteId " +
            "left join TrProgramMaster trProgramMaster\n" +
            "on trainingDeputationTracker.trProgramMasterId = trProgramMaster.trProgramMasterId " +
            "left join TrCourseMaster trCourseMaster\n" +
            "on trainingDeputationTracker.trCourseMasterId = trCourseMaster.trCourseMasterId " +
            "where trainingDeputationTracker.active = :isActive AND " +
            "(:joinColumn = 'trainingDeputationTracker.officialName' AND trainingDeputationTracker.officialName LIKE :searchText) OR " +
            "(:joinColumn = 'trainingDeputationTracker.mobileNumber' AND trainingDeputationTracker.mobileNumber LIKE :searchText)"
    )
    public Page<TrainingDeputationTrackerDTO> getSortedTrainingDeputations(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}
