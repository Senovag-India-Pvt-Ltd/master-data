package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.Relationship;
import com.sericulture.masterdata.model.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RelationshipRepository extends PagingAndSortingRepository<Relationship, Long> {
        public List<Relationship> findByRelationshipName(String relationshipName);

        public Relationship findByRelationshipNameAndActive(String relationshipName,boolean isActive);

        public Page<Relationship> findByActiveOrderByRelationshipIdAsc(boolean isActive, final Pageable pageable);

        public Relationship save(Relationship relationship);

        public Relationship findByRelationshipIdAndActive(long id, boolean isActive);

        public Relationship findByRelationshipIdAndActiveIn(@Param("relationshipId") long relationshipId, @Param("active") Set<Boolean> active);



}