package com.aidriveall.cms.repository;


import com.aidriveall.cms.domain.CommonTableField;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * Spring Data  repository for the CommonTableField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommonTableFieldRepository extends JpaRepository<CommonTableField, Long>, JpaSpecificationExecutor<CommonTableField> {

    CommonTableField findOneByCommonTableEntityNameAndEntityFieldName(String entityName, String entityFieldName);
    CommonTableField findOneByCommonTableIdAndEntityFieldName(Long commonTableId, String entityFieldName);
    // jhipster-needle-repository-add-method - JHipster will add getters and setters here, do not remove
}
