package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.CommonTableFieldService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.CommonTableFieldDTO;
import com.aidriveall.cms.service.dto.CommonTableFieldCriteria;
import com.aidriveall.cms.service.CommonTableFieldQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.CommonTableField}.
 */
@RestController
@RequestMapping("/api")
public class CommonTableFieldResource {

    private final Logger log = LoggerFactory.getLogger(CommonTableFieldResource.class);

    private static final String ENTITY_NAME = "modelConfigCommonTableField";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommonTableFieldService commonTableFieldService;

    private final CommonTableFieldQueryService commonTableFieldQueryService;

    public CommonTableFieldResource(CommonTableFieldService commonTableFieldService, CommonTableFieldQueryService commonTableFieldQueryService) {
        this.commonTableFieldService = commonTableFieldService;
        this.commonTableFieldQueryService = commonTableFieldQueryService;
    }

    /**
     * {@code POST  /common-table-fields} : Create a new commonTableField.
     *
     * @param commonTableFieldDTO the commonTableFieldDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commonTableFieldDTO, or with status {@code 400 (Bad Request)} if the commonTableField has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/common-table-fields")
    public ResponseEntity<CommonTableFieldDTO> createCommonTableField(@Valid @RequestBody CommonTableFieldDTO commonTableFieldDTO) throws URISyntaxException {
        log.debug("REST request to save CommonTableField : {}", commonTableFieldDTO);
        if (commonTableFieldDTO.getId() != null) {
            throw new BadRequestAlertException("A new commonTableField cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommonTableFieldDTO result = commonTableFieldService.save(commonTableFieldDTO);
        return ResponseEntity.created(new URI("/api/common-table-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /common-table-fields} : Updates an existing commonTableField.
     *
     * @param commonTableFieldDTO the commonTableFieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commonTableFieldDTO,
     * or with status {@code 400 (Bad Request)} if the commonTableFieldDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commonTableFieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/common-table-fields")
    public ResponseEntity<CommonTableFieldDTO> updateCommonTableField(@Valid @RequestBody CommonTableFieldDTO commonTableFieldDTO) throws URISyntaxException {
        log.debug("REST request to update CommonTableField : {}", commonTableFieldDTO);
        if (commonTableFieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommonTableFieldDTO result = commonTableFieldService.save(commonTableFieldDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commonTableFieldDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /common-table-fields} : get all the commonTableFields.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commonTableFields in body.
     */
    @GetMapping("/common-table-fields")
    public ResponseEntity<List<CommonTableFieldDTO>> getAllCommonTableFields(CommonTableFieldCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get CommonTableFields by criteria: {}", criteria);
        Page<CommonTableFieldDTO> page;
        if (listModelName != null) {
            page = commonTableFieldQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = commonTableFieldQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /common-table-fields/count} : count all the commonTableFields.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/common-table-fields/count")
    public ResponseEntity<Long> countCommonTableFields(CommonTableFieldCriteria criteria) {
        log.debug("REST request to count CommonTableFields by criteria: {}", criteria);
        return ResponseEntity.ok().body(commonTableFieldQueryService.countByCriteria(criteria));
    }


    /**
     * {@code GET  /common-table-fields/:id} : get the "id" commonTableField.
     *
     * @param id the id of the commonTableFieldDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commonTableFieldDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/common-table-fields/{id}")

    public ResponseEntity<CommonTableFieldDTO> getCommonTableField(@PathVariable Long id) {
        log.debug("REST request to get CommonTableField : {}", id);
        Optional<CommonTableFieldDTO> commonTableFieldDTO = commonTableFieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commonTableFieldDTO);
    }
    /**
     * GET  /common-table-fields/export : export the commonTableFields.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the commonTableFieldDTO, or with status 404 (Not Found)
     */
    @GetMapping("/common-table-fields/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<CommonTableFieldDTO> page = commonTableFieldService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            CommonTableFieldDTO.class, page.getContent());
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
     * POST  /common-table-fields/import : import the commonTableFields from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the commonTableFieldDTO, or with status 404 (Not Found)
     */
    @PostMapping("/common-table-fields/import")
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
        List<CommonTableFieldDTO> list = ExcelImportUtil.importExcel(savedFile, CommonTableFieldDTO.class, params);
        list.forEach(commonTableFieldService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /common-table-fields/:id} : delete the "id" commonTableField.
     *
     * @param id the id of the commonTableFieldDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/common-table-fields/{id}")
    public ResponseEntity<Void> deleteCommonTableField(@PathVariable Long id) {
        log.debug("REST request to delete CommonTableField : {}", id);
        commonTableFieldService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /common-table-fields} : delete all the "ids" CommonTableFields.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/common-table-fields")
    public ResponseEntity<Void> deleteCommonTableFieldsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete CommonTableFields : {}", ids);
        if (ids != null) {
            ids.forEach(commonTableFieldService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }


    /**
     * {@code PUT  /common-table-fields/specified-fields} : Updates an existing commonTableField by specified fields.
     *
     * @param commonTableFieldDTOAndSpecifiedFields the commonTableFieldDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commonTableFieldDTO,
     * or with status {@code 400 (Bad Request)} if the commonTableFieldDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commonTableFieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/common-table-fields/specified-fields")
    public ResponseEntity<CommonTableFieldDTO> updateCommonTableFieldBySpecifiedFields(@RequestBody CommonTableFieldDTOAndSpecifiedFields commonTableFieldDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update CommonTableField : {}", commonTableFieldDTOAndSpecifiedFields);
        CommonTableFieldDTO commonTableFieldDTO = commonTableFieldDTOAndSpecifiedFields.getCommonTableField();
        Set<String> specifiedFields = commonTableFieldDTOAndSpecifiedFields.getSpecifiedFields();
        if (commonTableFieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommonTableFieldDTO result = commonTableFieldService.updateBySpecifiedFields(commonTableFieldDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commonTableFieldDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /common-table-fields/specified-field} : Updates an existing commonTableField by specified field.
     *
     * @param commonTableFieldDTOAndSpecifiedFields the commonTableFieldDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commonTableFieldDTO,
     * or with status {@code 400 (Bad Request)} if the commonTableFieldDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commonTableFieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/common-table-fields/specified-field")
    public ResponseEntity<CommonTableFieldDTO> updateCommonTableFieldBySpecifiedField(@RequestBody CommonTableFieldDTOAndSpecifiedFields commonTableFieldDTOAndSpecifiedFields, CommonTableFieldCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update CommonTableField : {}", commonTableFieldDTOAndSpecifiedFields);
        CommonTableFieldDTO commonTableFieldDTO = commonTableFieldDTOAndSpecifiedFields.getCommonTableField();
        String fieldName = commonTableFieldDTOAndSpecifiedFields.getSpecifiedField();
        if (commonTableFieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommonTableFieldDTO result = commonTableFieldService.updateBySpecifiedField(commonTableFieldDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class CommonTableFieldDTOAndSpecifiedFields {
        private CommonTableFieldDTO commonTableField;
        private Set<String> specifiedFields;
        private String specifiedField;

        private CommonTableFieldDTO getCommonTableField() {
            return commonTableField;
        }

        private void setCommonTableField(CommonTableFieldDTO commonTableField) {
            this.commonTableField = commonTableField;
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
