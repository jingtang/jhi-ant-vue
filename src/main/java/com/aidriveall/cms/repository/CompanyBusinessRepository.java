package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.CompanyBusiness;

import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the CompanyBusiness entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyBusinessRepository extends EntityGraphJpaRepository<CompanyBusiness, Long>, EntityGraphJpaSpecificationExecutor<CompanyBusiness> {

    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
