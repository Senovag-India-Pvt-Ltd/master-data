package com.sericulture.masterdata.repository;


import com.sericulture.masterdata.model.dto.TrTraineeDTO;
import com.sericulture.masterdata.model.entity.TrTrainee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TrTraineeRepository extends PagingAndSortingRepository<TrTrainee, Long> {
    public List<TrTrainee> findByTrTraineeName(String trTraineeName);

    public List<TrTrainee> findByMobileNumber(String mobileNumber);

    public TrTrainee findByTrTraineeNameAndActive(String trTraineeName, boolean isActive);

    public Page<TrTrainee> findByActiveOrderByTrTraineeIdAsc(boolean isActive, final Pageable pageable);

    public Page<TrTrainee> findByActiveOrderByTrTraineeNameAsc(boolean isActive, final Pageable pageable);

    public TrTrainee save(TrTrainee trTrainee);

    public TrTrainee findByTrTraineeIdAndActive(long id, boolean isActive);

    public TrTrainee findByTrTraineeIdAndActiveIn(@Param("trTraineeId") long trTraineeId, @Param("active") Set<Boolean> active);

    public List<TrTrainee> findByActiveOrderByTrTraineeNameAsc(boolean isActive);

    public List<TrTrainee> findByTrScheduleIdAndActive(long trScheduleId, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.TrTraineeDTO(" +
            " trTrainee.trTraineeId," +
            " trTrainee.trScheduleId," +
            " trTrainee.trTraineeName," +
            " trTrainee.designationId," +
            " trTrainee.trOfficeId," +
            " trTrainee.gender," +
            " trTrainee.mobileNumber," +
            " trTrainee.place," +
            " trTrainee.stateId," +
            " trTrainee.districtId," +
            " trTrainee.talukId," +
            " trTrainee.hobliId," +
            " trTrainee.villageId," +
            " trTrainee.preTestScore," +
            " trTrainee.postTestScore," +
            " trTrainee.percentageImproved," +
            " designation.name," +
            " trOffice.trOfficeName," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " hobli.hobliName," +
            " village.villageName" +

            ") \n" +
            "from TrTrainee trTrainee\n" +
            "left join Designation designation\n" +
            "on trTrainee.designationId = designation.designationId " +
            "left join TrOffice trOffice\n" +
            "on trTrainee.trOfficeId = trOffice.trOfficeId " +
            "left join State state\n" +
            "on trTrainee.stateId= state.stateId " +
            "left join District district\n" +
            "on trTrainee.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on trTrainee.talukId = taluk.talukId " +
            "left join Hobli hobli\n" +
            "on trTrainee.hobliId = hobli.hobliId " +
            "left join Village village\n" +
            "on trTrainee.villageId = village.villageId " +
            "where trTrainee.active = :isActive " +
            "ORDER BY trTrainee.trTraineeName ASC"
    )
    Page<TrTraineeDTO> getByActiveOrderByTrTraineeIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TrTraineeDTO(" +
            " trTrainee.trTraineeId," +
            " trTrainee.trScheduleId," +
            " trTrainee.trTraineeName," +
            " trTrainee.designationId," +
            " trTrainee.trOfficeId," +
            " trTrainee.gender," +
            " trTrainee.mobileNumber," +
            " trTrainee.place," +
            " trTrainee.stateId," +
            " trTrainee.districtId," +
            " trTrainee.talukId," +
            " trTrainee.hobliId," +
            " trTrainee.villageId," +
            " trTrainee.preTestScore," +
            " trTrainee.postTestScore," +
            " trTrainee.percentageImproved," +
            " designation.name," +
            " trOffice.trOfficeName," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " hobli.hobliName," +
            " village.villageName" +

            ") \n" +
            "from TrTrainee trTrainee\n" +
            "left join Designation designation\n" +
            "on trTrainee.designationId = designation.designationId " +
            "left join TrOffice trOffice\n" +
            "on trTrainee.trOfficeId = trOffice.trOfficeId " +
            "left join State state\n" +
            "on trTrainee.stateId= state.stateId " +
            "left join District district\n" +
            "on trTrainee.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on trTrainee.talukId = taluk.talukId " +
            "left join Hobli hobli\n" +
            "on trTrainee.hobliId = hobli.hobliId " +
            "left join Village village\n" +
            "on trTrainee.villageId = village.villageId " +
            "where trTrainee.active = :isActive AND trTrainee.trTraineeId = :id "
    )
    public TrTraineeDTO getByTrTraineeIdAndActive(long id, boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.TrTraineeDTO(" +
            " trTrainee.trTraineeId," +
            " trTrainee.trScheduleId," +
            " trTrainee.trTraineeName," +
            " trTrainee.designationId," +
            " trTrainee.trOfficeId," +
            " trTrainee.gender," +
            " trTrainee.mobileNumber," +
            " trTrainee.place," +
            " trTrainee.stateId," +
            " trTrainee.districtId," +
            " trTrainee.talukId," +
            " trTrainee.hobliId," +
            " trTrainee.villageId," +
            " trTrainee.preTestScore," +
            " trTrainee.postTestScore," +
            " trTrainee.percentageImproved," +
            " designation.name," +
            " trOffice.trOfficeName," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " hobli.hobliName," +
            " village.villageName" +

            ") \n" +
            "from TrTrainee trTrainee\n" +
            "left join Designation designation\n" +
            "on trTrainee.designationId = designation.designationId " +
            "left join TrOffice trOffice\n" +
            "on trTrainee.trOfficeId = trOffice.trOfficeId " +
            "left join State state\n" +
            "on trTrainee.stateId= state.stateId " +
            "left join District district\n" +
            "on trTrainee.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on trTrainee.talukId = taluk.talukId " +
            "left join Hobli hobli\n" +
            "on trTrainee.hobliId = hobli.hobliId " +
            "left join Village village\n" +
            "on trTrainee.villageId = village.villageId " +
            "where trTrainee.active = :isActive AND " +
            "(:joinColumn = 'trTrainee.trTraineeName' AND trTrainee.trTraineeName LIKE :searchText) OR " +
            "(:joinColumn = 'trTrainee.mobileNumber' AND trTrainee.mobileNumber LIKE :searchText)"
    )
    public Page<TrTraineeDTO> getSortedTrTrainee(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.TrTraineeDTO(" +
            " trTrainee.trTraineeId," +
            " trTrainee.trScheduleId," +
            " trTrainee.trTraineeName," +
            " trTrainee.designationId," +
            " trTrainee.trOfficeId," +
            " trTrainee.gender," +
            " trTrainee.mobileNumber," +
            " trTrainee.place," +
            " trTrainee.stateId," +
            " trTrainee.districtId," +
            " trTrainee.talukId," +
            " trTrainee.hobliId," +
            " trTrainee.villageId," +
            " trTrainee.preTestScore," +
            " trTrainee.postTestScore," +
            " trTrainee.percentageImproved," +
            " designation.name," +
            " trOffice.trOfficeName," +
            " state.stateName," +
            " district.districtName," +
            " taluk.talukName," +
            " hobli.hobliName," +
            " village.villageName" +

            ") \n" +
            "from TrTrainee trTrainee\n" +
            "left join Designation designation\n" +
            "on trTrainee.designationId = designation.designationId " +
            "left join TrOffice trOffice\n" +
            "on trTrainee.trOfficeId = trOffice.trOfficeId " +
            "left join State state\n" +
            "on trTrainee.stateId= state.stateId " +
            "left join District district\n" +
            "on trTrainee.districtId = district.districtId " +
            "left join Taluk taluk\n" +
            "on trTrainee.talukId = taluk.talukId " +
            "left join Hobli hobli\n" +
            "on trTrainee.hobliId = hobli.hobliId " +
            "left join Village village\n" +
            "on trTrainee.villageId = village.villageId " +
            "where trTrainee.active = :isActive AND trTrainee.trScheduleId = :id")
    public List <TrTraineeDTO> getByTrScheduleIdAndActive(@Param("id") long id, @Param("isActive") boolean isActive);
}
