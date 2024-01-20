package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.TrScheduleDTO;
import com.sericulture.masterdata.model.dto.TrTrainingDTO;
import com.sericulture.masterdata.model.entity.TrTrainee;
import com.sericulture.masterdata.model.entity.TrTraining;
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
public interface TrTrainingRepository extends PagingAndSortingRepository<TrTraining, Long> {
    public Page<TrTraining> findByActiveOrderByTrTrainingIdAsc(boolean isActive, final Pageable pageable);

    public TrTraining save(TrTraining trTraining);

    public TrTraining findByTrTrainingIdAndActive(long trTrainingId, boolean isActive);

    public TrTraining findByTrTrainingIdAndActiveIn(@Param("trTrainingId") long trTrainingId, @Param("active") Set<Boolean> active);

    public List<TrTraining> findByActiveOrderByTrTrainingIdAsc(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TrTrainingDTO(" +
            " trTraining.trTrainingId," +
            " trTraining.trStakeholderType," +
            " trTraining.trInstitutionMasterId," +
            " trTraining.trGroupMasterId," +
            " trTraining.trProgramMasterId," +
            " trTraining.trCourseMasterId," +
            " trTraining.trTrainingDate," +
            " trTraining.trDuration," +
            " trTraining.trPeriod," +
            " trTraining.userMasterId," +
            " trInstitutionMaster.trInstitutionMasterName," +
            " trGroupMaster.trGroupMasterName," +
            " trProgramMaster.trProgramMasterName," +
            " trCourseMaster.trCourseMasterName," +
            " userMaster.username" +

            ") \n" +
            "from TrTraining trTraining\n" +
            "left join TrInstitutionMaster trInstitutionMaster\n" +
            "on trTraining.trInstitutionMasterId = trInstitutionMaster.trInstitutionMasterId " +
            "left join TrGroupMaster trGroupMaster\n" +
            "on trTraining.trGroupMasterId = trGroupMaster.trGroupMasterId " +
            "left join TrProgramMaster trProgramMaster\n" +
            "on trTraining.trProgramMasterId = trProgramMaster.trProgramMasterId " +
            "left join TrCourseMaster trCourseMaster\n" +
            "on trTraining.trCourseMasterId = trCourseMaster.trCourseMasterId " +
            "left join UserMaster userMaster\n" +
            "on trTraining.userMasterId = userMaster.userMasterId " +
            "where trTraining.active = :isActive " +
            "ORDER BY trTraining.trTrainingId ASC"
    )
    Page<TrTrainingDTO> getByActiveOrderByTrTrainingIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TrTrainingDTO(" +
            " trTraining.trTrainingId," +
            " trTraining.trStakeholderType," +
            " trTraining.trInstitutionMasterId," +
            " trTraining.trGroupMasterId," +
            " trTraining.trProgramMasterId," +
            " trTraining.trCourseMasterId," +
            " trTraining.trTrainingDate," +
            " trTraining.trDuration," +
            " trTraining.trPeriod," +
            " trTraining.userMasterId," +
            " trInstitutionMaster.trInstitutionMasterName," +
            " trGroupMaster.trGroupMasterName," +
            " trProgramMaster.trProgramMasterName," +
            " trCourseMaster.trCourseMasterName," +
            " userMaster.username" +

            ") \n" +
            "from TrTraining trTraining\n" +
            "left join TrInstitutionMaster trInstitutionMaster\n" +
            "on trTraining.trInstitutionMasterId = trInstitutionMaster.trInstitutionMasterId " +
            "left join TrGroupMaster trGroupMaster\n" +
            "on trTraining.trGroupMasterId = trGroupMaster.trGroupMasterId " +
            "left join TrProgramMaster trProgramMaster\n" +
            "on trTraining.trProgramMasterId = trProgramMaster.trProgramMasterId " +
            "left join TrCourseMaster trCourseMaster\n" +
            "on trTraining.trCourseMasterId = trCourseMaster.trCourseMasterId " +
            "left join UserMaster userMaster\n" +
            "on trTraining.userMasterId = userMaster.userMasterId " +
            "where trTraining.active = :isActive AND trTraining.trTrainingId = :id "
    )
    public TrTrainingDTO getByTrTrainingIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TrTrainingDTO(" +
            " trTraining.trTrainingId," +
            " trTraining.trStakeholderType," +
            " trTraining.trInstitutionMasterId," +
            " trTraining.trGroupMasterId," +
            " trTraining.trProgramMasterId," +
            " trTraining.trCourseMasterId," +
            " trTraining.trTrainingDate," +
            " trTraining.trDuration," +
            " trTraining.trPeriod," +
            " trTraining.userMasterId," +
            " trInstitutionMaster.trInstitutionMasterName," +
            " trGroupMaster.trGroupMasterName," +
            " trProgramMaster.trProgramMasterName," +
            " trCourseMaster.trCourseMasterName," +
            " userMaster.username" +

            ") \n" +
            "from TrTraining trTraining\n" +
            "left join TrInstitutionMaster trInstitutionMaster\n" +
            "on trTraining.trInstitutionMasterId = trInstitutionMaster.trInstitutionMasterId " +
            "left join TrGroupMaster trGroupMaster\n" +
            "on trTraining.trGroupMasterId = trGroupMaster.trGroupMasterId " +
            "left join TrProgramMaster trProgramMaster\n" +
            "on trTraining.trProgramMasterId = trProgramMaster.trProgramMasterId " +
            "left join TrCourseMaster trCourseMaster\n" +
            "on trTraining.trCourseMasterId = trCourseMaster.trCourseMasterId " +
            "left join UserMaster userMaster\n" +
            "on trTraining.userMasterId = userMaster.userMasterId " +
            "where trTraining.active = :isActive AND " +
            "(:joinColumn = 'trTraining.trTrainingId' AND trTraining.trTrainingId LIKE :searchText) OR " +
            "(:joinColumn = 'trTraining.trStakeholderType' AND trTraining.trStakeholderType LIKE :searchText)"
    )
    public Page<TrTrainingDTO> getSortedTrTrainings(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}
