package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.State;
import com.sericulture.masterdata.model.entity.VendorMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface VendorMasterRepository extends PagingAndSortingRepository<VendorMaster, Long> {
    public List<VendorMaster> findByVendorMasterName(String vendorMasterName);

    public List<VendorMaster> findByVendorMasterNameAndVendorMasterIdIsNot(String vendorMasterName,long vendorMasterId);


    public VendorMaster findByVendorMasterNameAndActive(String vendorMasterName,boolean isActive);

    public Page<VendorMaster> findByActiveOrderByVendorMasterNameAsc(boolean isActive, final Pageable pageable);

    public VendorMaster save(VendorMaster vendorMaster);

    public VendorMaster findByVendorMasterIdAndActive(long id, boolean isActive);

    public VendorMaster findByVendorMasterIdAndActiveIn(@Param("vendorMasterId") long vendorMasterId, @Param("active") Set<Boolean> active);

    public List<VendorMaster> findByActive(boolean isActive);
}
