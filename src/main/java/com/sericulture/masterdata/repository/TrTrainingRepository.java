package com.sericulture.masterdata.repository;


import com.sericulture.masterdata.model.dto.TrScheduleDTO;
import com.sericulture.masterdata.model.dto.TrTrainingDTO;
import com.sericulture.masterdata.model.entity.TrSchedule;
import com.sericulture.masterdata.model.entity.TrTraining;
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
    public List<TrTraining> findByTrTraineeName(String trTraineeName);

    public TrTraining findByTrTraineeNameAndActive(String trTraineeName,boolean isActive);

    public Page<TrTraining> findByActiveOrderByTrTraineeIdAsc(boolean isActive, final Pageable pageable);

    public Page<TrTraining> findByActiveOrderByTrTraineeNameAsc(boolean isActive, final Pageable pageable);

    public TrTraining save(TrTraining trTraining);

    public TrTraining findByTrTraineeIdAndActive(long id, boolean isActive);

    public TrTraining findByTrTraineeIdAndActiveIn(@Param("trTraineeId") long trTraineeId, @Param("active") Set<Boolean> active);

    public List<TrTraining> findByActiveOrderByTrTraineeNameAsc(boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TrTrainingDTO(" +
            " trTraining.trTraineeId," +
            " trTraining.trTraineeName," +
            " trTraining.designationId," +
            " trTraining.trOfficeId," +
            " trTraining.gender," +
            " trTraining.mobileNumber," +
            " trTraining.place," +
            " trTraining.stateId," +
            " trTraining.districtId," +
            " trTraining.talukId," +
            " trTraining.hobliId," +
            " trTraining.villageId," +
            " trTraining.preTestScore," +
            " trTraining.postTestScore," +
            " trTraining.percentageImproved," +
            " designation.name," +
            " trOffice.trOfficeName," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " hobli.hobliName," +
            " village.villageName" +

            ") \n" +
            "from TrTraining trTraining\n" +
            "left join Designation designation\n" +
            "on trTraining.designationId = designation.designationId " +
            "left join TrOffice trOffice\n" +
            "on trTraining.trOfficeId = trOffice.trOfficeId " +
            "left join State state\n" +
            "on trTraining.stateId= state.stateId " +
            "left join District district\n" +
            "on trTraining.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on trTraining.talukId = taluk.talukId " +
            "left join Hobli hobli\n" +
            "on trTraining.hobliId = hobli.hobliId " +
            "left join Village village\n" +
            "on trTraining.villageId = village.villageId " +
            "where trTraining.active = :isActive " +
            "ORDER BY trTraining.trTraineeName ASC"
    )
    Page<TrTrainingDTO> getByActiveOrderByTrTraineeIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TrScheduleDTO(" +
            " trTraining.trTraineeId," +
            " trTraining.trTraineeName," +
            " trTraining.designationId," +
            " trTraining.trOfficeId," +
            " trTraining.gender," +
            " trTraining.mobileNumber," +
            " trTraining.place," +
            " trTraining.stateId," +
            " trTraining.districtId," +
            " trTraining.talukId," +
            " trTraining.hobliId," +
            " trTraining.villageId," +
            " trTraining.preTestScore," +
            " trTraining.postTestScore," +
            " trTraining.percentageImproved," +
            " designation.name," +
            " trOffice.trOfficeName," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " hobli.hobliName," +
            " village.villageName" +
            ") \n" +
            "from TrTraining trTraining\n" +
            "left join Designation designation\n" +
            "on trTraining.designationId = designation.designationId " +
            "left join TrOffice trOffice\n" +
            "on trTraining.trOfficeId = trOffice.trOfficeId " +
            "left join State state\n" +
            "on trTraining.stateId= state.stateId " +
            "left join District district\n" +
            "on trTraining.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on trTraining.talukId = taluk.talukId " +
            "left join Hobli hobli\n" +
            "on trTraining.hobliId = hobli.hobliId " +
            "left join Village village\n" +
            "on trTraining.villageId = village.villageId " +
            "where trTraining.active = :isActive AND trTraining.trTraineeId = :id "
    )
    public TrTrainingDTO getByTrTraineeIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TrScheduleDTO(" +
            " trTraining.trTraineeId," +
            " trTraining.trTraineeName," +
            " trTraining.designationId," +
            " trTraining.trOfficeId," +
            " trTraining.gender," +
            " trTraining.mobileNumber," +
            " trTraining.place," +
            " trTraining.stateId," +
            " trTraining.districtId," +
            " trTraining.talukId," +
            " trTraining.hobliId," +
            " trTraining.villageId," +
            " trTraining.preTestScore," +
            " trTraining.postTestScore," +
            " trTraining.percentageImproved," +
            " designation.name," +
            " trOffice.trOfficeName," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " hobli.hobliName," +
            " village.villageName" +

            ") \n" +
            "from TrTraining trTraining\n" +
            "left join Designation designation\n" +
            "on trTraining.designationId = designation.designationId " +
            "left join TrOffice trOffice\n" +
            "on trTraining.trOfficeId = trOffice.trOfficeId " +
            "left join State state\n" +
            "on trTraining.stateId= state.stateId " +
            "left join District district\n" +
            "on trTraining.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on trTraining.talukId = taluk.talukId " +
            "left join Hobli hobli\n" +
            "on trTraining.hobliId = hobli.hobliId " +
            "left join Village village\n" +
            "on trTraining.villageId = village.villageId " +
            "where trTraining.active = :isActive AND " +
            "(:joinColumn = 'trTraining.trTraineeName' AND trTraining.trTraineeName LIKE :searchText) OR " +
            "(:joinColumn = 'trTraining.trTraineeId' AND trTraining.trTraineeId LIKE :searchText)"
    )
    public Page<TrTrainingDTO> getSortedTrTrainee(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}
