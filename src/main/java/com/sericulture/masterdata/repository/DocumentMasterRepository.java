package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.DocumentMaster;
import com.sericulture.masterdata.model.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DocumentMasterRepository extends PagingAndSortingRepository<DocumentMaster, Long> {

    public List<DocumentMaster> findByDocumentMasterName(String documentMasterName);
    public List<DocumentMaster> findByDocumentMasterNameAndDocumentMasterIdIsNot(String documentMasterName, long documentMasterId );


    public DocumentMaster findByDocumentMasterNameAndActive(String documentMasterName,boolean isActive);

    public Page<DocumentMaster> findByActiveOrderByDocumentMasterNameAsc(boolean isActive, final Pageable pageable);

    public DocumentMaster save(DocumentMaster documentMaster);

    public DocumentMaster findByDocumentMasterIdAndActive(long id, boolean isActive);

    public DocumentMaster findByDocumentMasterIdAndActiveIn(@Param("documentMasterId") long documentMasterId, @Param("active") Set<Boolean> active);

    public List<DocumentMaster> findByActive(boolean isActive);
}
