package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.BusinessType;

import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the BusinessType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessTypeRepository extends EntityGraphJpaRepository<BusinessType, Long>, EntityGraphJpaSpecificationExecutor<BusinessType> {

    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
