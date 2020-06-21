package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.ViewPermission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the ViewPermission entity.
 */
@Repository
public interface ViewPermissionRepository extends EntityGraphJpaRepository<ViewPermission, Long>, EntityGraphJpaSpecificationExecutor<ViewPermission> {
    Page<ViewPermission> findAllByParentIsNull(Pageable pageable);

    @Query(value = "select distinct viewPermission from ViewPermission viewPermission left join fetch viewPermission.apiPermissions",
        countQuery = "select count(distinct viewPermission) from ViewPermission viewPermission")
    Page<ViewPermission> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct viewPermission from ViewPermission viewPermission left join fetch viewPermission.apiPermissions")
    List<ViewPermission> findAllWithEagerRelationships();

    @Query("select viewPermission from ViewPermission viewPermission left join fetch viewPermission.apiPermissions where viewPermission.id =:id")
    Optional<ViewPermission> findOneWithEagerRelationships(@Param("id") Long id);

    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
