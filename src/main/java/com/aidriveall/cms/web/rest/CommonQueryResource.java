package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.CommonQueryService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.CommonQueryDTO;
import com.aidriveall.cms.service.dto.CommonQueryCriteria;
import com.aidriveall.cms.service.CommonQueryQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;

import org.apache.poi.ss.usermodel.Workbook;


import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;

// jhipster-needle-add-import - JHipster will add getters and setters here, do not remove

/**
 * REST controller for managing {@link com.aidriveall.cms.domain.CommonQuery}.
 */
@RestController
@RequestMapping("/api")
public class CommonQueryResource {

    private final Logger log = LoggerFactory.getLogger(CommonQueryResource.class);

    private static final String ENTITY_NAME = "commonQueryCommonQuery";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommonQueryService commonQueryService;

    private final CommonQueryQueryService commonQueryQueryService;

    public CommonQueryResource(CommonQueryService commonQueryService, CommonQueryQueryService commonQueryQueryService) {
        this.commonQueryService = commonQueryService;
        this.commonQueryQueryService = commonQueryQueryService;
    }

    /**
     * {@code POST  /common-queries} : Create a new commonQuery.
     *
     * @param commonQueryDTO the commonQueryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commonQueryDTO, or with status {@code 400 (Bad Request)} if the commonQuery has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/common-queries")
    public ResponseEntity<CommonQueryDTO> createCommonQuery(@Valid @RequestBody CommonQueryDTO commonQueryDTO) throws URISyntaxException {
        log.debug("REST request to save CommonQuery : {}", commonQueryDTO);
        if (commonQueryDTO.getId() != null) {
            throw new BadRequestAlertException("A new commonQuery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommonQueryDTO result = commonQueryService.save(commonQueryDTO);
        return ResponseEntity.created(new URI("/api/common-queries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /common-queries} : Updates an existing commonQuery.
     *
     * @param commonQueryDTO the commonQueryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commonQueryDTO,
     * or with status {@code 400 (Bad Request)} if the commonQueryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commonQueryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/common-queries")
    public ResponseEntity<CommonQueryDTO> updateCommonQuery(@Valid @RequestBody CommonQueryDTO commonQueryDTO) throws URISyntaxException {
        log.debug("REST request to update CommonQuery : {}", commonQueryDTO);
        if (commonQueryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommonQueryDTO result = commonQueryService.save(commonQueryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commonQueryDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /common-queries} : get all the commonQueries.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commonQueries in body.
     */
    @GetMapping("/common-queries")
    public ResponseEntity<List<CommonQueryDTO>> getAllCommonQueries(CommonQueryCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get CommonQueries by criteria: {}", criteria);
        Page<CommonQueryDTO> page;
        if (listModelName != null) {
            page = commonQueryQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = commonQueryQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /common-queries/count} : count all the commonQueries.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/common-queries/count")
    public ResponseEntity<Long> countCommonQueries(CommonQueryCriteria criteria) {
        log.debug("REST request to count CommonQueries by criteria: {}", criteria);
        return ResponseEntity.ok().body(commonQueryQueryService.countByCriteria(criteria));
    }


    /**
     * {@code GET  /common-queries/:id} : get the "id" commonQuery.
     *
     * @param id the id of the commonQueryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commonQueryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/common-queries/{id}")

    public ResponseEntity<CommonQueryDTO> getCommonQuery(@PathVariable Long id) {
        log.debug("REST request to get CommonQuery : {}", id);
        Optional<CommonQueryDTO> commonQueryDTO = commonQueryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commonQueryDTO);
    }
    /**
     * GET  /common-queries/export : export the commonQueries.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the commonQueryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/common-queries/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<CommonQueryDTO> page = commonQueryService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            CommonQueryDTO.class, page.getContent());
        File savefile = new File("export");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("export/personDTO_2018_09_10.xls");
        workbook.write(fos);
        fos.close();
        return ResponseEntity.ok().build();
    }

    /**
     * POST  /common-queries/import : import the commonQueries from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the commonQueryDTO, or with status 404 (Not Found)
     */
    @PostMapping("/common-queries/import")
    public ResponseEntity<Void> exportToExcel(MultipartFile file) throws IOException {
        String fileRealName = file.getOriginalFilename();//获得原始文件名;
        int pointIndex =  fileRealName.lastIndexOf(".");//点号的位置
        String fileSuffix = fileRealName.substring(pointIndex);//截取文件后缀
        String fileNewName = UUID.randomUUID().toString();//文件new名称时间戳
        String saveFileName = fileNewName.concat(fileSuffix);//文件存取名
        String filePath  = "import" ;
        File path = new File(filePath); //判断文件路径下的文件夹是否存在，不存在则创建
        if (!path.exists()) {
            path.mkdirs();
        }
        File savedFile = new File(filePath,saveFileName);
        file.transferTo(savedFile);
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<CommonQueryDTO> list = ExcelImportUtil.importExcel(savedFile, CommonQueryDTO.class, params);
        list.forEach(commonQueryService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /common-queries/:id} : delete the "id" commonQuery.
     *
     * @param id the id of the commonQueryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/common-queries/{id}")
    public ResponseEntity<Void> deleteCommonQuery(@PathVariable Long id) {
        log.debug("REST request to delete CommonQuery : {}", id);
        commonQueryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /common-queries} : delete all the "ids" CommonQueries.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/common-queries")
    public ResponseEntity<Void> deleteCommonQueriesByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete CommonQueries : {}", ids);
        if (ids != null) {
            ids.forEach(commonQueryService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }

    @GetMapping("/common-queries/modifier/current-user")
    public ResponseEntity<List<CommonQueryDTO>> findByModifierIsCurrentUser() {
        log.debug("REST request to get CommonQuery for current user. ");
        List<CommonQueryDTO> result = commonQueryService.findByModifierIsCurrentUser();
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /common-queries/specified-fields} : Updates an existing commonQuery by specified fields.
     *
     * @param commonQueryDTOAndSpecifiedFields the commonQueryDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commonQueryDTO,
     * or with status {@code 400 (Bad Request)} if the commonQueryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commonQueryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/common-queries/specified-fields")
    public ResponseEntity<CommonQueryDTO> updateCommonQueryBySpecifiedFields(@RequestBody CommonQueryDTOAndSpecifiedFields commonQueryDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update CommonQuery : {}", commonQueryDTOAndSpecifiedFields);
        CommonQueryDTO commonQueryDTO = commonQueryDTOAndSpecifiedFields.getCommonQuery();
        Set<String> specifiedFields = commonQueryDTOAndSpecifiedFields.getSpecifiedFields();
        if (commonQueryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommonQueryDTO result = commonQueryService.updateBySpecifiedFields(commonQueryDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commonQueryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /common-queries/specified-field} : Updates an existing commonQuery by specified field.
     *
     * @param commonQueryDTOAndSpecifiedFields the commonQueryDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commonQueryDTO,
     * or with status {@code 400 (Bad Request)} if the commonQueryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commonQueryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/common-queries/specified-field")
    public ResponseEntity<CommonQueryDTO> updateCommonQueryBySpecifiedField(@RequestBody CommonQueryDTOAndSpecifiedFields commonQueryDTOAndSpecifiedFields, CommonQueryCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update CommonQuery : {}", commonQueryDTOAndSpecifiedFields);
        CommonQueryDTO commonQueryDTO = commonQueryDTOAndSpecifiedFields.getCommonQuery();
        String fieldName = commonQueryDTOAndSpecifiedFields.getSpecifiedField();
        if (commonQueryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommonQueryDTO result = commonQueryService.updateBySpecifiedField(commonQueryDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class CommonQueryDTOAndSpecifiedFields {
        private CommonQueryDTO commonQuery;
        private Set<String> specifiedFields;
        private String specifiedField;

        private CommonQueryDTO getCommonQuery() {
            return commonQuery;
        }

        private void setCommonQuery(CommonQueryDTO commonQuery) {
            this.commonQuery = commonQuery;
        }

        private Set<String> getSpecifiedFields() {
            return specifiedFields;
        }

        private void setSpecifiedFields(Set<String> specifiedFields) {
            this.specifiedFields = specifiedFields;
        }

        public String getSpecifiedField() {
            return specifiedField;
        }

        public void setSpecifiedField(String specifiedField) {
            this.specifiedField = specifiedField;
        }
    }
}
