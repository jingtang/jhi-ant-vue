package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.CompanyCustomer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the CompanyCustomer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyCustomerRepository extends EntityGraphJpaRepository<CompanyCustomer, Long>, EntityGraphJpaSpecificationExecutor<CompanyCustomer> {
    Page<CompanyCustomer> findAllByParentIsNull(Pageable pageable);

    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
