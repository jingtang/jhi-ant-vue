package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.Owner;
import com.aidriveall.cms.domain.ProcessEntityRelation;

import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the ProcessEntityRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessEntityRelationRepository extends EntityGraphJpaRepository<ProcessEntityRelation, Long>, EntityGraphJpaSpecificationExecutor<ProcessEntityRelation> {

    @Query("select p.processInstanceId from ProcessEntityRelation p where p.entityId =:entityId and p.entityType=:entityType")
    String getProcessInstanceIdByEntity(@Param("entityId") Long entityId, @Param("entityType") String entityType);


    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
