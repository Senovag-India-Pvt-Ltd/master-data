package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.MachineTypeMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MachineTypeMasterRepository extends PagingAndSortingRepository<MachineTypeMaster, Long> {
    public List<MachineTypeMaster> findByMachineTypeName(String machineTypeName);

    public MachineTypeMaster findByMachineTypeNameAndActive(String machineTypeName,boolean isActive);

    public Page<MachineTypeMaster> findByActiveOrderByMachineTypeIdAsc(boolean isActive, final Pageable pageable);

    public MachineTypeMaster save(MachineTypeMaster machineType);

    public MachineTypeMaster findByMachineTypeIdAndActive(long id, boolean isActive);

    public MachineTypeMaster findByMachineTypeIdAndActiveIn(@Param("machineTypeId") long machineTypeId, @Param("active") Set<Boolean> active);

}