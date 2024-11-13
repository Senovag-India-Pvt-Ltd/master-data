package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.MulberryTargetType;
import com.sericulture.masterdata.model.entity.MulberrySource;
import com.sericulture.masterdata.model.entity.MulberryTargetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface MulberryTargetTypeRepository extends PagingAndSortingRepository<MulberryTargetType, Long> {

    public List<MulberryTargetType> findByMulberryTargetTypeName(String mulberryTargetTypeName);

    public List<MulberryTargetType> findByMulberryTargetTypeNameAndActive(String MulberryTargetTypeName,boolean isActive);

    public Page<MulberryTargetType> findByActiveOrderByMulberryTargetTypeIdAsc(boolean isActive, final Pageable pageable);

    public MulberryTargetType save(MulberryTargetType mulberryTargetType);

    public MulberryTargetType findByMulberryTargetTypeIdAndActive(long mulberryTargetTypeId, boolean isActive);

    public MulberryTargetType findByMulberryTargetTypeIdAndActiveIn(@Param("mulberryTargetTypeId") long mulberryTargetTypeId, @Param("active") Set<Boolean> active);

    public List<MulberryTargetType> findByActiveOrderByMulberryTargetTypeNameAsc(boolean isActive);

    public Page<MulberryTargetType> findByActiveOrderByMulberryTargetTypeNameAsc(boolean isActive, final Pageable pageable);

    public List<MulberryTargetType> findByActive(boolean isActive);
}
