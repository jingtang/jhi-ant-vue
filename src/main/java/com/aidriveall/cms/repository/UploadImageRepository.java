package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.UploadImage;

import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the UploadImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UploadImageRepository extends EntityGraphJpaRepository<UploadImage, Long>, EntityGraphJpaSpecificationExecutor<UploadImage> {

    @Query("select uploadImage from UploadImage uploadImage where uploadImage.user.login = ?#{principal.username}")
    List<UploadImage> findByUserIsCurrentUser();

    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
