package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.DisinfectantMaster;
import com.sericulture.masterdata.model.entity.TrModeMaster;
import com.sericulture.masterdata.model.entity.TrProgramMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DisinfectantMasterRepository extends PagingAndSortingRepository<DisinfectantMaster, Long> {


    public List<DisinfectantMaster> findByDisinfectantMasterNameAndDisinfectantMasterNameInKannada(String disinfectantMasterName, String disinfectantMasterInKannada);

    public List<DisinfectantMaster> findByDisinfectantMasterNameAndDisinfectantMasterNameInKannadaAndDisinfectantMasterIdIsNot(String disinfectantMasterName,String disinfectantMasterInKannada,long disinfectantMasterId);

    public DisinfectantMaster findByDisinfectantMasterNameAndActive(String disinfectantMasterName,boolean isActive);

    public Page<DisinfectantMaster> findByActiveOrderByDisinfectantMasterNameAsc(boolean isActive, final Pageable pageable);

    public DisinfectantMaster save(DisinfectantMaster disinfectantMaster);

    public DisinfectantMaster findByDisinfectantMasterIdAndActive(long id, boolean isActive);

    public DisinfectantMaster findByDisinfectantMasterIdAndActiveIn(@Param("disinfectantMasterId") long disinfectantMasterId, @Param("active") Set<Boolean> active);

    public List<DisinfectantMaster> findByActiveOrderByDisinfectantMasterNameAsc(boolean isActive);
}
