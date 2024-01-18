package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.MarketMasterDTO;
import com.sericulture.masterdata.model.dto.RaceMarketMasterDTO;
import com.sericulture.masterdata.model.dto.TrScheduleDTO;
import com.sericulture.masterdata.model.entity.RaceMarketMaster;
import com.sericulture.masterdata.model.entity.TrCourseMaster;
import com.sericulture.masterdata.model.entity.TrModeMaster;
import com.sericulture.masterdata.model.entity.TrSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrScheduleRepository extends PagingAndSortingRepository<TrSchedule,  Long> {
    public List<TrSchedule> findByTrName(String trName);

    public TrSchedule findByTrNameAndActive(String trName,boolean isActive);

    public Page<TrSchedule> findByActiveOrderByTrScheduleIdAsc(boolean isActive, final Pageable pageable);

    public Page<TrSchedule> findByActiveOrderByTrNameAsc(boolean isActive, final Pageable pageable);

    public TrSchedule save(TrSchedule trSchedule);

    public TrSchedule findByTrScheduleIdAndActive(long id, boolean isActive);

    public TrSchedule findByTrScheduleIdAndActiveIn(@Param("trScheduleId") long trScheduleId, @Param("active") Set<Boolean> active);

    public List<TrSchedule> findByActiveOrderByTrNameAsc(boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TrScheduleDTO(" +
            " trSchedule.trScheduleId," +
            " trSchedule.trInstitutionMasterId," +
            " trSchedule.trGroupMasterId," +
            " trSchedule.trProgramMasterId," +
            " trSchedule.trCourseMasterId," +
            " trSchedule.trModeMasterId," +
            " trSchedule.trDuration," +
            " trSchedule.trPeriod," +
            " trSchedule.trNoOfParticipant," +
            " trSchedule.trName," +
            " trSchedule.trUploadPath," +
            " trSchedule.trStartDate," +
            " trSchedule.trDateOfCompletion," +
            " trInstitutionMaster.trInstitutionMasterName," +
            " trGroupMaster.trGroupMasterName," +
            " trProgramMaster.trProgramMasterName," +
            " trCourseMaster.trCourseMasterName," +
            " trModeMaster.trModeMasterName" +

            ") \n" +
            "from TrSchedule trSchedule\n" +
            "left join TrInstitutionMaster trInstitutionMaster\n" +
            "on trSchedule.trInstitutionMasterId = trInstitutionMaster.trInstitutionMasterId " +
            "left join TrGroupMaster trGroupMaster\n" +
            "on trSchedule.trGroupMasterId = trGroupMaster.trGroupMasterId " +
            "left join TrProgramMaster trProgramMaster\n" +
            "on trSchedule.trProgramMasterId = trProgramMaster.trProgramMasterId " +
            "left join TrCourseMaster trCourseMaster\n" +
            "on trSchedule.trCourseMasterId = trCourseMaster.trCourseMasterId " +
            "left join TrModeMaster trModeMaster\n" +
            "on trSchedule.trModeMasterId = trModeMaster.trModeMasterId " +
            "where trSchedule.active = :isActive " +
            "ORDER BY trSchedule.trName ASC"
    )
    Page<TrScheduleDTO> getByActiveOrderByTrScheduleIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TrScheduleDTO(" +
            " trSchedule.trScheduleId," +
            " trSchedule.trInstitutionMasterId," +
            " trSchedule.trGroupMasterId," +
            " trSchedule.trProgramMasterId," +
            " trSchedule.trCourseMasterId," +
            " trSchedule.trModeMasterId," +
            " trSchedule.trDuration," +
            " trSchedule.trPeriod," +
            " trSchedule.trNoOfParticipant," +
            " trSchedule.trName," +
            " trSchedule.trUploadPath," +
            " trSchedule.trStartDate," +
            " trSchedule.trDateOfCompletion," +
            " trInstitutionMaster.trInstitutionMasterName," +
            " trGroupMaster.trGroupMasterName," +
            " trProgramMaster.trProgramMasterName," +
            " trCourseMaster.trCourseMasterName," +
            " trModeMaster.trModeMasterName" +

            ") \n" +
            "from TrSchedule trSchedule\n" +
            "left join TrInstitutionMaster trInstitutionMaster\n" +
            "on trSchedule.trInstitutionMasterId = trInstitutionMaster.trInstitutionMasterId " +
            "left join TrGroupMaster trGroupMaster\n" +
            "on trSchedule.trGroupMasterId = trGroupMaster.trGroupMasterId " +
            "left join TrProgramMaster trProgramMaster\n" +
            "on trSchedule.trProgramMasterId = trProgramMaster.trProgramMasterId " +
            "left join TrCourseMaster trCourseMaster\n" +
            "on trSchedule.trCourseMasterId = trCourseMaster.trCourseMasterId " +
            "left join TrModeMaster trModeMaster\n" +
            "on trSchedule.trModeMasterId = trModeMaster.trModeMasterId " +
            "where trSchedule.active = :isActive AND trSchedule.trScheduleId = :id "
    )
    public TrScheduleDTO getByTrScheduleIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TrScheduleDTO(" +
            " trSchedule.trScheduleId," +
            " trSchedule.trInstitutionMasterId," +
            " trSchedule.trGroupMasterId," +
            " trSchedule.trProgramMasterId," +
            " trSchedule.trCourseMasterId," +
            " trSchedule.trModeMasterId," +
            " trSchedule.trDuration," +
            " trSchedule.trPeriod," +
            " trSchedule.trNoOfParticipant," +
            " trSchedule.trName," +
            " trSchedule.trUploadPath," +
            " trSchedule.trStartDate," +
            " trSchedule.trDateOfCompletion," +
            " trInstitutionMaster.trInstitutionMasterName," +
            " trGroupMaster.trGroupMasterName," +
            " trProgramMaster.trProgramMasterName," +
            " trCourseMaster.trCourseMasterName," +
            " trModeMaster.trModeMasterName" +

            ") \n" +
            "from TrSchedule trSchedule\n" +
            "left join TrInstitutionMaster trInstitutionMaster\n" +
            "on trSchedule.trInstitutionMasterId = trInstitutionMaster.trInstitutionMasterId " +
            "left join TrGroupMaster trGroupMaster\n" +
            "on trSchedule.trGroupMasterId = trGroupMaster.trGroupMasterId " +
            "left join TrProgramMaster trProgramMaster\n" +
            "on trSchedule.trProgramMasterId = trProgramMaster.trProgramMasterId " +
            "left join TrCourseMaster trCourseMaster\n" +
            "on trSchedule.trCourseMasterId = trCourseMaster.trCourseMasterId " +
            "left join TrModeMaster trModeMaster\n" +
            "on trSchedule.trModeMasterId = trModeMaster.trModeMasterId " +
            "where trSchedule.active = :isActive AND " +
            "(:joinColumn = 'trSchedule.trName' AND trSchedule.trName LIKE :searchText) OR " +
            "(:joinColumn = 'trSchedule.trScheduleId' AND trSchedule.trScheduleId LIKE :searchText)"
    )
    public Page<TrScheduleDTO> getSortedTrSchedules(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}



