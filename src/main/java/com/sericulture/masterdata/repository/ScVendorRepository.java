package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.ScVendor;
import com.sericulture.masterdata.model.entity.ScVendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScVendorRepository  extends PagingAndSortingRepository<ScVendor, Long> {
    public List<ScVendor> findByName(String name);

    public List<ScVendor> findByNameAndNameInKannada(String name,String nameInKannada);
    public List<ScVendor> findByNameAndNameInKannadaAndType(String name,String nameInKannada, Long type);

    public List<ScVendor> findByActiveAndNameAndNameInKannadaIsNot(boolean a,String name,String nameInKannada);

    public List<ScVendor> findByActiveAndNameAndNameInKannadaAndType(boolean a,String name,String nameInKannada, long type);

    public ScVendor findByNameAndActive(String name,boolean isActive);

    public Page<ScVendor> findByActiveOrderByNameAsc(boolean isActive, final Pageable pageable);

    public ScVendor save(ScVendor scVendor);

    public ScVendor findByScVendorIdAndActive(long id, boolean isActive);

    public ScVendor findByScVendorIdAndActiveIn(@Param("scVendorId") long scVendorId, @Param("active") Set<Boolean> active);

    public List<ScVendor> findByActive(boolean isActive);
}
