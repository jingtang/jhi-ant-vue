package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.CommonQuery;

import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the CommonQuery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommonQueryRepository extends EntityGraphJpaRepository<CommonQuery, Long>, EntityGraphJpaSpecificationExecutor<CommonQuery> {

    @Query("select commonQuery from CommonQuery commonQuery where commonQuery.modifier.login = ?#{principal.username}")
    List<CommonQuery> findByModifierIsCurrentUser();

    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
