package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.BusinessTypeService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.BusinessTypeDTO;
import com.aidriveall.cms.service.dto.BusinessTypeCriteria;
import com.aidriveall.cms.service.BusinessTypeQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.BusinessType}.
 */
@RestController
@RequestMapping("/api")
public class BusinessTypeResource {

    private final Logger log = LoggerFactory.getLogger(BusinessTypeResource.class);

    private static final String ENTITY_NAME = "companyBusinessType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessTypeService businessTypeService;

    private final BusinessTypeQueryService businessTypeQueryService;

    public BusinessTypeResource(BusinessTypeService businessTypeService, BusinessTypeQueryService businessTypeQueryService) {
        this.businessTypeService = businessTypeService;
        this.businessTypeQueryService = businessTypeQueryService;
    }

    /**
     * {@code POST  /business-types} : Create a new businessType.
     *
     * @param businessTypeDTO the businessTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessTypeDTO, or with status {@code 400 (Bad Request)} if the businessType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-types")
    public ResponseEntity<BusinessTypeDTO> createBusinessType(@RequestBody BusinessTypeDTO businessTypeDTO) throws URISyntaxException {
        log.debug("REST request to save BusinessType : {}", businessTypeDTO);
        if (businessTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new businessType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessTypeDTO result = businessTypeService.save(businessTypeDTO);
        return ResponseEntity.created(new URI("/api/business-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-types} : Updates an existing businessType.
     *
     * @param businessTypeDTO the businessTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessTypeDTO,
     * or with status {@code 400 (Bad Request)} if the businessTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-types")
    public ResponseEntity<BusinessTypeDTO> updateBusinessType(@RequestBody BusinessTypeDTO businessTypeDTO) throws URISyntaxException {
        log.debug("REST request to update BusinessType : {}", businessTypeDTO);
        if (businessTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessTypeDTO result = businessTypeService.save(businessTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessTypeDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /business-types} : get all the businessTypes.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessTypes in body.
     */
    @GetMapping("/business-types")
    public ResponseEntity<List<BusinessTypeDTO>> getAllBusinessTypes(BusinessTypeCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get BusinessTypes by criteria: {}", criteria);
        Page<BusinessTypeDTO> page;
        if (listModelName != null) {
            page = businessTypeQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = businessTypeQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /business-types/count} : count all the businessTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/business-types/count")
    public ResponseEntity<Long> countBusinessTypes(BusinessTypeCriteria criteria) {
        log.debug("REST request to count BusinessTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(businessTypeQueryService.countByCriteria(criteria));
    }


    /**
     * {@code GET  /business-types/:id} : get the "id" businessType.
     *
     * @param id the id of the businessTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-types/{id}")

    public ResponseEntity<BusinessTypeDTO> getBusinessType(@PathVariable Long id) {
        log.debug("REST request to get BusinessType : {}", id);
        Optional<BusinessTypeDTO> businessTypeDTO = businessTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(businessTypeDTO);
    }
    /**
     * GET  /business-types/export : export the businessTypes.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the businessTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/business-types/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<BusinessTypeDTO> page = businessTypeService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            BusinessTypeDTO.class, page.getContent());
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
     * POST  /business-types/import : import the businessTypes from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the businessTypeDTO, or with status 404 (Not Found)
     */
    @PostMapping("/business-types/import")
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
        List<BusinessTypeDTO> list = ExcelImportUtil.importExcel(savedFile, BusinessTypeDTO.class, params);
        list.forEach(businessTypeService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /business-types/:id} : delete the "id" businessType.
     *
     * @param id the id of the businessTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-types/{id}")
    public ResponseEntity<Void> deleteBusinessType(@PathVariable Long id) {
        log.debug("REST request to delete BusinessType : {}", id);
        businessTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /business-types} : delete all the "ids" BusinessTypes.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/business-types")
    public ResponseEntity<Void> deleteBusinessTypesByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete BusinessTypes : {}", ids);
        if (ids != null) {
            ids.forEach(businessTypeService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }


    /**
     * {@code PUT  /business-types/specified-fields} : Updates an existing businessType by specified fields.
     *
     * @param businessTypeDTOAndSpecifiedFields the businessTypeDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessTypeDTO,
     * or with status {@code 400 (Bad Request)} if the businessTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-types/specified-fields")
    public ResponseEntity<BusinessTypeDTO> updateBusinessTypeBySpecifiedFields(@RequestBody BusinessTypeDTOAndSpecifiedFields businessTypeDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update BusinessType : {}", businessTypeDTOAndSpecifiedFields);
        BusinessTypeDTO businessTypeDTO = businessTypeDTOAndSpecifiedFields.getBusinessType();
        Set<String> specifiedFields = businessTypeDTOAndSpecifiedFields.getSpecifiedFields();
        if (businessTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessTypeDTO result = businessTypeService.updateBySpecifiedFields(businessTypeDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, businessTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-types/specified-field} : Updates an existing businessType by specified field.
     *
     * @param businessTypeDTOAndSpecifiedFields the businessTypeDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessTypeDTO,
     * or with status {@code 400 (Bad Request)} if the businessTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-types/specified-field")
    public ResponseEntity<BusinessTypeDTO> updateBusinessTypeBySpecifiedField(@RequestBody BusinessTypeDTOAndSpecifiedFields businessTypeDTOAndSpecifiedFields, BusinessTypeCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update BusinessType : {}", businessTypeDTOAndSpecifiedFields);
        BusinessTypeDTO businessTypeDTO = businessTypeDTOAndSpecifiedFields.getBusinessType();
        String fieldName = businessTypeDTOAndSpecifiedFields.getSpecifiedField();
        if (businessTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessTypeDTO result = businessTypeService.updateBySpecifiedField(businessTypeDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class BusinessTypeDTOAndSpecifiedFields {
        private BusinessTypeDTO businessType;
        private Set<String> specifiedFields;
        private String specifiedField;

        private BusinessTypeDTO getBusinessType() {
            return businessType;
        }

        private void setBusinessType(BusinessTypeDTO businessType) {
            this.businessType = businessType;
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
