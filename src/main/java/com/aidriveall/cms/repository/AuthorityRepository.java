package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.Authority;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the Authority entity.
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority> {
    Page<Authority> findAllByParentIsNull(Pageable pageable);

    @Query(value = "select distinct authority from Authority authority left join fetch authority.users left join fetch authority.viewPermissions",
        countQuery = "select count(distinct authority) from Authority authority")
    Page<Authority> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct authority from Authority authority left join fetch authority.users left join fetch authority.viewPermissions")
    List<Authority> findAllWithEagerRelationships();

    @Query("select authority from Authority authority left join fetch authority.users left join fetch authority.viewPermissions where authority.id =:id")
    Optional<Authority> findOneWithEagerRelationships(@Param("id") Long id);

    Optional<Authority> findFirstByCode(String code);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
