package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.DataDictionary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the DataDictionary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataDictionaryRepository extends EntityGraphJpaRepository<DataDictionary, Long>, EntityGraphJpaSpecificationExecutor<DataDictionary> {
    Page<DataDictionary> findAllByParentIsNull(Pageable pageable);

    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
