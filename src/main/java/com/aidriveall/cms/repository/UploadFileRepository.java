package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.UploadFile;

import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the UploadFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UploadFileRepository extends EntityGraphJpaRepository<UploadFile, Long>, EntityGraphJpaSpecificationExecutor<UploadFile> {

    @Query("select uploadFile from UploadFile uploadFile where uploadFile.user.login = ?#{principal.username}")
    List<UploadFile> findByUserIsCurrentUser();

    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
