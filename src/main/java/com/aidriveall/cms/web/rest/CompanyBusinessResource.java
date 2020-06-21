package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.CompanyBusinessService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.CompanyBusinessDTO;
import com.aidriveall.cms.service.dto.CompanyBusinessCriteria;
import com.aidriveall.cms.service.CompanyBusinessQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.CompanyBusiness}.
 */
@RestController
@RequestMapping("/api")
public class CompanyBusinessResource {

    private final Logger log = LoggerFactory.getLogger(CompanyBusinessResource.class);

    private static final String ENTITY_NAME = "companyCompanyBusiness";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyBusinessService companyBusinessService;

    private final CompanyBusinessQueryService companyBusinessQueryService;

    public CompanyBusinessResource(CompanyBusinessService companyBusinessService, CompanyBusinessQueryService companyBusinessQueryService) {
        this.companyBusinessService = companyBusinessService;
        this.companyBusinessQueryService = companyBusinessQueryService;
    }

    /**
     * {@code POST  /company-businesses} : Create a new companyBusiness.
     *
     * @param companyBusinessDTO the companyBusinessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyBusinessDTO, or with status {@code 400 (Bad Request)} if the companyBusiness has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-businesses")
    public ResponseEntity<CompanyBusinessDTO> createCompanyBusiness(@RequestBody CompanyBusinessDTO companyBusinessDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyBusiness : {}", companyBusinessDTO);
        if (companyBusinessDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyBusiness cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyBusinessDTO result = companyBusinessService.save(companyBusinessDTO);
        return ResponseEntity.created(new URI("/api/company-businesses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-businesses} : Updates an existing companyBusiness.
     *
     * @param companyBusinessDTO the companyBusinessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyBusinessDTO,
     * or with status {@code 400 (Bad Request)} if the companyBusinessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyBusinessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-businesses")
    public ResponseEntity<CompanyBusinessDTO> updateCompanyBusiness(@RequestBody CompanyBusinessDTO companyBusinessDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyBusiness : {}", companyBusinessDTO);
        if (companyBusinessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyBusinessDTO result = companyBusinessService.save(companyBusinessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyBusinessDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /company-businesses} : get all the companyBusinesses.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyBusinesses in body.
     */
    @GetMapping("/company-businesses")
    public ResponseEntity<List<CompanyBusinessDTO>> getAllCompanyBusinesses(CompanyBusinessCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get CompanyBusinesses by criteria: {}", criteria);
        Page<CompanyBusinessDTO> page;
        if (listModelName != null) {
            page = companyBusinessQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = companyBusinessQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /company-businesses/count} : count all the companyBusinesses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/company-businesses/count")
    public ResponseEntity<Long> countCompanyBusinesses(CompanyBusinessCriteria criteria) {
        log.debug("REST request to count CompanyBusinesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(companyBusinessQueryService.countByCriteria(criteria));
    }


    /**
     * {@code GET  /company-businesses/:id} : get the "id" companyBusiness.
     *
     * @param id the id of the companyBusinessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyBusinessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-businesses/{id}")

    public ResponseEntity<CompanyBusinessDTO> getCompanyBusiness(@PathVariable Long id) {
        log.debug("REST request to get CompanyBusiness : {}", id);
        Optional<CompanyBusinessDTO> companyBusinessDTO = companyBusinessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyBusinessDTO);
    }
    /**
     * GET  /company-businesses/export : export the companyBusinesses.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the companyBusinessDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-businesses/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<CompanyBusinessDTO> page = companyBusinessService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            CompanyBusinessDTO.class, page.getContent());
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
     * POST  /company-businesses/import : import the companyBusinesses from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the companyBusinessDTO, or with status 404 (Not Found)
     */
    @PostMapping("/company-businesses/import")
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
        List<CompanyBusinessDTO> list = ExcelImportUtil.importExcel(savedFile, CompanyBusinessDTO.class, params);
        list.forEach(companyBusinessService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /company-businesses/:id} : delete the "id" companyBusiness.
     *
     * @param id the id of the companyBusinessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-businesses/{id}")
    public ResponseEntity<Void> deleteCompanyBusiness(@PathVariable Long id) {
        log.debug("REST request to delete CompanyBusiness : {}", id);
        companyBusinessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /company-businesses} : delete all the "ids" CompanyBusinesses.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/company-businesses")
    public ResponseEntity<Void> deleteCompanyBusinessesByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete CompanyBusinesses : {}", ids);
        if (ids != null) {
            ids.forEach(companyBusinessService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }


    /**
     * {@code PUT  /company-businesses/specified-fields} : Updates an existing companyBusiness by specified fields.
     *
     * @param companyBusinessDTOAndSpecifiedFields the companyBusinessDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyBusinessDTO,
     * or with status {@code 400 (Bad Request)} if the companyBusinessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyBusinessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-businesses/specified-fields")
    public ResponseEntity<CompanyBusinessDTO> updateCompanyBusinessBySpecifiedFields(@RequestBody CompanyBusinessDTOAndSpecifiedFields companyBusinessDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update CompanyBusiness : {}", companyBusinessDTOAndSpecifiedFields);
        CompanyBusinessDTO companyBusinessDTO = companyBusinessDTOAndSpecifiedFields.getCompanyBusiness();
        Set<String> specifiedFields = companyBusinessDTOAndSpecifiedFields.getSpecifiedFields();
        if (companyBusinessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyBusinessDTO result = companyBusinessService.updateBySpecifiedFields(companyBusinessDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyBusinessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-businesses/specified-field} : Updates an existing companyBusiness by specified field.
     *
     * @param companyBusinessDTOAndSpecifiedFields the companyBusinessDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyBusinessDTO,
     * or with status {@code 400 (Bad Request)} if the companyBusinessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyBusinessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-businesses/specified-field")
    public ResponseEntity<CompanyBusinessDTO> updateCompanyBusinessBySpecifiedField(@RequestBody CompanyBusinessDTOAndSpecifiedFields companyBusinessDTOAndSpecifiedFields, CompanyBusinessCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update CompanyBusiness : {}", companyBusinessDTOAndSpecifiedFields);
        CompanyBusinessDTO companyBusinessDTO = companyBusinessDTOAndSpecifiedFields.getCompanyBusiness();
        String fieldName = companyBusinessDTOAndSpecifiedFields.getSpecifiedField();
        if (companyBusinessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyBusinessDTO result = companyBusinessService.updateBySpecifiedField(companyBusinessDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class CompanyBusinessDTOAndSpecifiedFields {
        private CompanyBusinessDTO companyBusiness;
        private Set<String> specifiedFields;
        private String specifiedField;

        private CompanyBusinessDTO getCompanyBusiness() {
            return companyBusiness;
        }

        private void setCompanyBusiness(CompanyBusinessDTO companyBusiness) {
            this.companyBusiness = companyBusiness;
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
