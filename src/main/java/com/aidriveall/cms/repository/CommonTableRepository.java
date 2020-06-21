package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.CommonTable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;
// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the CommonTable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommonTableRepository extends JpaRepository<CommonTable, Long>, JpaSpecificationExecutor<CommonTable> {

    @Query("select commonTable from CommonTable commonTable where commonTable.creator.login = ?#{principal.username}")
    List<CommonTable> findByCreatorIsCurrentUser();

    Optional<CommonTable> findOneByEntityName(String entityName);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
