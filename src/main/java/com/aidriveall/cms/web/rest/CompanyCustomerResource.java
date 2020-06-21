package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.CompanyCustomerService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.CompanyCustomerDTO;
import com.aidriveall.cms.service.dto.CompanyCustomerCriteria;
import com.aidriveall.cms.service.CompanyCustomerQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.CompanyCustomer}.
 */
@RestController
@RequestMapping("/api")
public class CompanyCustomerResource {

    private final Logger log = LoggerFactory.getLogger(CompanyCustomerResource.class);

    private static final String ENTITY_NAME = "companyCompanyCustomer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyCustomerService companyCustomerService;

    private final CompanyCustomerQueryService companyCustomerQueryService;

    public CompanyCustomerResource(CompanyCustomerService companyCustomerService, CompanyCustomerQueryService companyCustomerQueryService) {
        this.companyCustomerService = companyCustomerService;
        this.companyCustomerQueryService = companyCustomerQueryService;
    }

    /**
     * {@code POST  /company-customers} : Create a new companyCustomer.
     *
     * @param companyCustomerDTO the companyCustomerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyCustomerDTO, or with status {@code 400 (Bad Request)} if the companyCustomer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-customers")
    public ResponseEntity<CompanyCustomerDTO> createCompanyCustomer(@RequestBody CompanyCustomerDTO companyCustomerDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyCustomer : {}", companyCustomerDTO);
        if (companyCustomerDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyCustomer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyCustomerDTO result = companyCustomerService.save(companyCustomerDTO);
        return ResponseEntity.created(new URI("/api/company-customers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-customers} : Updates an existing companyCustomer.
     *
     * @param companyCustomerDTO the companyCustomerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyCustomerDTO,
     * or with status {@code 400 (Bad Request)} if the companyCustomerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyCustomerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-customers")
    public ResponseEntity<CompanyCustomerDTO> updateCompanyCustomer(@RequestBody CompanyCustomerDTO companyCustomerDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyCustomer : {}", companyCustomerDTO);
        if (companyCustomerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyCustomerDTO result = companyCustomerService.save(companyCustomerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyCustomerDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /company-customers} : get all the companyCustomers.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyCustomers in body.
     */
    @GetMapping("/company-customers")
    public ResponseEntity<List<CompanyCustomerDTO>> getAllCompanyCustomers(CompanyCustomerCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get CompanyCustomers by criteria: {}", criteria);
        Page<CompanyCustomerDTO> page;
        if (listModelName != null) {
            page = companyCustomerQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = companyCustomerQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /company-customers/count} : count all the companyCustomers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/company-customers/count")
    public ResponseEntity<Long> countCompanyCustomers(CompanyCustomerCriteria criteria) {
        log.debug("REST request to count CompanyCustomers by criteria: {}", criteria);
        return ResponseEntity.ok().body(companyCustomerQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /company-customers/tree : get all the companyCustomers for parent is null.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of companyCustomers in body
     */
    @GetMapping("/company-customers/tree")
    public ResponseEntity<List<CompanyCustomerDTO>> getAllCompanyCustomersofTree(Pageable pageable) {
        log.debug("REST request to get a page of CompanyCustomers");
        Page<CompanyCustomerDTO> page = companyCustomerService.findAllTop(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-customers/:id} : get the "id" companyCustomer.
     *
     * @param id the id of the companyCustomerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyCustomerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-customers/{id}")

    public ResponseEntity<CompanyCustomerDTO> getCompanyCustomer(@PathVariable Long id) {
        log.debug("REST request to get CompanyCustomer : {}", id);
        Optional<CompanyCustomerDTO> companyCustomerDTO = companyCustomerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyCustomerDTO);
    }
    /**
     * GET  /company-customers/export : export the companyCustomers.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the companyCustomerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-customers/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<CompanyCustomerDTO> page = companyCustomerService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            CompanyCustomerDTO.class, page.getContent());
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
     * POST  /company-customers/import : import the companyCustomers from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the companyCustomerDTO, or with status 404 (Not Found)
     */
    @PostMapping("/company-customers/import")
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
        List<CompanyCustomerDTO> list = ExcelImportUtil.importExcel(savedFile, CompanyCustomerDTO.class, params);
        list.forEach(companyCustomerService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /company-customers/:id} : delete the "id" companyCustomer.
     *
     * @param id the id of the companyCustomerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-customers/{id}")
    public ResponseEntity<Void> deleteCompanyCustomer(@PathVariable Long id) {
        log.debug("REST request to delete CompanyCustomer : {}", id);
        companyCustomerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /company-customers} : delete all the "ids" CompanyCustomers.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/company-customers")
    public ResponseEntity<Void> deleteCompanyCustomersByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete CompanyCustomers : {}", ids);
        if (ids != null) {
            ids.forEach(companyCustomerService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }


    /**
     * {@code PUT  /company-customers/specified-fields} : Updates an existing companyCustomer by specified fields.
     *
     * @param companyCustomerDTOAndSpecifiedFields the companyCustomerDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyCustomerDTO,
     * or with status {@code 400 (Bad Request)} if the companyCustomerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyCustomerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-customers/specified-fields")
    public ResponseEntity<CompanyCustomerDTO> updateCompanyCustomerBySpecifiedFields(@RequestBody CompanyCustomerDTOAndSpecifiedFields companyCustomerDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update CompanyCustomer : {}", companyCustomerDTOAndSpecifiedFields);
        CompanyCustomerDTO companyCustomerDTO = companyCustomerDTOAndSpecifiedFields.getCompanyCustomer();
        Set<String> specifiedFields = companyCustomerDTOAndSpecifiedFields.getSpecifiedFields();
        if (companyCustomerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyCustomerDTO result = companyCustomerService.updateBySpecifiedFields(companyCustomerDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyCustomerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-customers/specified-field} : Updates an existing companyCustomer by specified field.
     *
     * @param companyCustomerDTOAndSpecifiedFields the companyCustomerDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyCustomerDTO,
     * or with status {@code 400 (Bad Request)} if the companyCustomerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyCustomerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-customers/specified-field")
    public ResponseEntity<CompanyCustomerDTO> updateCompanyCustomerBySpecifiedField(@RequestBody CompanyCustomerDTOAndSpecifiedFields companyCustomerDTOAndSpecifiedFields, CompanyCustomerCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update CompanyCustomer : {}", companyCustomerDTOAndSpecifiedFields);
        CompanyCustomerDTO companyCustomerDTO = companyCustomerDTOAndSpecifiedFields.getCompanyCustomer();
        String fieldName = companyCustomerDTOAndSpecifiedFields.getSpecifiedField();
        if (companyCustomerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompanyCustomerDTO result = companyCustomerService.updateBySpecifiedField(companyCustomerDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class CompanyCustomerDTOAndSpecifiedFields {
        private CompanyCustomerDTO companyCustomer;
        private Set<String> specifiedFields;
        private String specifiedField;

        private CompanyCustomerDTO getCompanyCustomer() {
            return companyCustomer;
        }

        private void setCompanyCustomer(CompanyCustomerDTO companyCustomer) {
            this.companyCustomer = companyCustomer;
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
