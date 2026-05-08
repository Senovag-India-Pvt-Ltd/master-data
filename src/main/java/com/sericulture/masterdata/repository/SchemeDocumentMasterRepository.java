package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.SchemeDocumentMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SchemeDocumentMasterRepository extends PagingAndSortingRepository<SchemeDocumentMaster, Long> {

    List<SchemeDocumentMaster> findByScSchemeDetailsIdAndScSubSchemeDetailsIdAndDocumentId(
            Integer scSchemeDetailsId, Integer scSubSchemeDetailsId, Integer documentId);

    List<SchemeDocumentMaster> findByScSchemeDetailsIdAndScSubSchemeDetailsIdAndDocumentIdAndSchemeDocumentIdIsNot(
            Integer scSchemeDetailsId, Integer scSubSchemeDetailsId, Integer documentId, long schemeDocumentId);

    List<SchemeDocumentMaster> findByActiveAndScSchemeDetailsIdAndScSubSchemeDetailsIdAndDocumentId(
            boolean active, Integer scSchemeDetailsId, Integer scSubSchemeDetailsId, Integer documentId);

    Page<SchemeDocumentMaster> findByActiveOrderBySchemeDocumentIdAsc(boolean isActive, Pageable pageable);

    SchemeDocumentMaster save(SchemeDocumentMaster schemeDocumentMaster);

    SchemeDocumentMaster findBySchemeDocumentIdAndActive(long id, boolean isActive);

    SchemeDocumentMaster findBySchemeDocumentIdAndActiveIn(
            @Param("schemeDocumentId") long schemeDocumentId, @Param("active") Set<Boolean> active);

    List<SchemeDocumentMaster> findByActive(boolean isActive);
}
