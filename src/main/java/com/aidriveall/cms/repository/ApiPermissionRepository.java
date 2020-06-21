package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.ApiPermission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the ApiPermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiPermissionRepository extends JpaRepository<ApiPermission, Long>, JpaSpecificationExecutor<ApiPermission> {
    Page<ApiPermission> findAllByParentIsNull(Pageable pageable);

    Optional<ApiPermission> findOneByCode(String groupCode);
    @Query("select viewPermission.apiPermissions from ViewPermission viewPermission join viewPermission.authorities a join a.users u where u.login = ?#{principal.username}")

    List<ApiPermission> findAllApiPermissionsByCurrentUser();
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
