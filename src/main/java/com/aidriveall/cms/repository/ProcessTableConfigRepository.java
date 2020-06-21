package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.ProcessTableConfig;

import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the ProcessTableConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessTableConfigRepository extends EntityGraphJpaRepository<ProcessTableConfig, Long>, EntityGraphJpaSpecificationExecutor<ProcessTableConfig> {

    @Query("select processTableConfig from ProcessTableConfig processTableConfig where processTableConfig.creator.login = ?#{principal.username}")
    List<ProcessTableConfig> findByCreatorIsCurrentUser();

    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
