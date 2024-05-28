package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.dto.ScComponentDTO;
import com.sericulture.masterdata.model.entity.ScComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ScComponentRepository extends PagingAndSortingRepository<ScComponent, Long> {
    public List<ScComponent> findByScComponentName(String scComponentName);

    public List<ScComponent> findByActiveAndScComponentNameAndScComponentIdIsNot(boolean a,String scComponentName,long scComponentId );

    public ScComponent findByScComponentNameAndActive(String scComponentName,boolean isActive);

    public Page<ScComponent> findByActiveOrderByScComponentNameAsc(boolean isActive, final Pageable pageable);

    public ScComponent save(ScComponent scComponent);

    public ScComponent findByScComponentIdAndActive(long id, boolean isActive);

    public ScComponent findByScComponentIdAndActiveIn(@Param("scComponentId") long scComponentId, @Param("active") Set<Boolean> active);
    public List<ScComponent> findByActive(boolean isActive);

    @Query("select new com.sericulture.masterdata.model.dto.ScComponentDTO(" +
            " scComponent.scComponentId," +
            " scComponent.scSubSchemeDetailsId," +
            " scComponent.scComponentName," +
            " scSubSchemeDetails.subSchemeName" +
            ") \n" +
            "from ScComponent scComponent\n" +
            "left join ScSubSchemeDetails scSubSchemeDetails\n" +
            "on scComponent.scSubSchemeDetailsId = scSubSchemeDetails.scSubSchemeDetailsId " +
            "where scComponent.active = :isActive " +
            "ORDER BY scComponent.scComponentId ASC"
    )
    Page<ScComponentDTO> getByActiveOrderByScComponentIdAsc(@Param("isActive") boolean isActive, final Pageable pageable);

    @Query("select new com.sericulture.masterdata.model.dto.ScComponentDTO(" +
            " scComponent.scComponentId," +
            " scComponent.scSubSchemeDetailsId," +
            " scComponent.scComponentName," +
            " scSubSchemeDetails.subSchemeName" +
            ") \n" +
            "from ScComponent scComponent\n" +
            "left join ScSubSchemeDetails scSubSchemeDetails\n" +
            "on scComponent.scSubSchemeDetailsId = scSubSchemeDetails.scSubSchemeDetailsId " +
            "where scComponent.active = :isActive AND scComponent.scComponentId = :id "
    )
    public ScComponentDTO getByScComponentIdAndActive(long id, boolean isActive);


    @Query("select new com.sericulture.masterdata.model.dto.ScComponentDTO(" +
            " scComponent.scComponentId," +
            " scComponent.scSubSchemeDetailsId," +
            " scComponent.scComponentName," +
            " scSubSchemeDetails.subSchemeName" +
            ") \n" +
            "from ScComponent scComponent\n" +
            "left join ScSubSchemeDetails scSubSchemeDetails\n" +
            "on scComponent.scSubSchemeDetailsId = scSubSchemeDetails.scSubSchemeDetailsId " +
            "where scComponent.active = :isActive AND " +
            "(:joinColumn = 'scSubSchemeDetails.subSchemeName' AND scSubSchemeDetails.subSchemeName LIKE :searchText)"

    )
    public Page<ScComponentDTO> getSortedScComponent(@Param("joinColumn") String joinColumn, @Param("searchText") String searchText, @Param("isActive") boolean isActive, Pageable pageable);
}
