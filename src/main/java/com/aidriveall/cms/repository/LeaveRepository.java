package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.Leave;

import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the Leave entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveRepository extends EntityGraphJpaRepository<Leave, Long>, EntityGraphJpaSpecificationExecutor<Leave> {

    @Query("select leave from Leave leave where leave.creator.login = ?#{principal.username}")
    List<Leave> findByCreatorIsCurrentUser();

    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
