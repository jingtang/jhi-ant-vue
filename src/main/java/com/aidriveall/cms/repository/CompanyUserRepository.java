package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.CompanyUser;

import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the CompanyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyUserRepository extends EntityGraphJpaRepository<CompanyUser, Long>, EntityGraphJpaSpecificationExecutor<CompanyUser> {

    @Query("select companyUser from CompanyUser companyUser where companyUser.user.login = ?#{principal.username}")
    List<CompanyUser> findByUserIsCurrentUser();

    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
