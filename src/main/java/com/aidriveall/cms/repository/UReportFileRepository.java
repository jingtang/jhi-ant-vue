package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.UReportFile;

import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the UReportFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UReportFileRepository extends EntityGraphJpaRepository<UReportFile, Long>, EntityGraphJpaSpecificationExecutor<UReportFile> {

    Boolean existsByName(String name);

    Optional<UReportFile> getByName(String name);

    void deleteByName(String name);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
