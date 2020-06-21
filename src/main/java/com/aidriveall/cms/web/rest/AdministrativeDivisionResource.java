package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.AdministrativeDivisionService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.AdministrativeDivisionDTO;
import com.aidriveall.cms.service.dto.AdministrativeDivisionCriteria;
import com.aidriveall.cms.service.AdministrativeDivisionQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.AdministrativeDivision}.
 */
@RestController
@RequestMapping("/api")
public class AdministrativeDivisionResource {

    private final Logger log = LoggerFactory.getLogger(AdministrativeDivisionResource.class);

    private static final String ENTITY_NAME = "settingsAdministrativeDivision";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdministrativeDivisionService administrativeDivisionService;

    private final AdministrativeDivisionQueryService administrativeDivisionQueryService;

    public AdministrativeDivisionResource(AdministrativeDivisionService administrativeDivisionService, AdministrativeDivisionQueryService administrativeDivisionQueryService) {
        this.administrativeDivisionService = administrativeDivisionService;
        this.administrativeDivisionQueryService = administrativeDivisionQueryService;
    }

    /**
     * {@code POST  /administrative-divisions} : Create a new administrativeDivision.
     *
     * @param administrativeDivisionDTO the administrativeDivisionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new administrativeDivisionDTO, or with status {@code 400 (Bad Request)} if the administrativeDivision has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/administrative-divisions")
    public ResponseEntity<AdministrativeDivisionDTO> createAdministrativeDivision(@RequestBody AdministrativeDivisionDTO administrativeDivisionDTO) throws URISyntaxException {
        log.debug("REST request to save AdministrativeDivision : {}", administrativeDivisionDTO);
        if (administrativeDivisionDTO.getId() != null) {
            throw new BadRequestAlertException("A new administrativeDivision cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdministrativeDivisionDTO result = administrativeDivisionService.save(administrativeDivisionDTO);
        return ResponseEntity.created(new URI("/api/administrative-divisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /administrative-divisions} : Updates an existing administrativeDivision.
     *
     * @param administrativeDivisionDTO the administrativeDivisionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrativeDivisionDTO,
     * or with status {@code 400 (Bad Request)} if the administrativeDivisionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the administrativeDivisionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/administrative-divisions")
    public ResponseEntity<AdministrativeDivisionDTO> updateAdministrativeDivision(@RequestBody AdministrativeDivisionDTO administrativeDivisionDTO) throws URISyntaxException {
        log.debug("REST request to update AdministrativeDivision : {}", administrativeDivisionDTO);
        if (administrativeDivisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdministrativeDivisionDTO result = administrativeDivisionService.save(administrativeDivisionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrativeDivisionDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /administrative-divisions} : get all the administrativeDivisions.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of administrativeDivisions in body.
     */
    @GetMapping("/administrative-divisions")
    public ResponseEntity<List<AdministrativeDivisionDTO>> getAllAdministrativeDivisions(AdministrativeDivisionCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get AdministrativeDivisions by criteria: {}", criteria);
        Page<AdministrativeDivisionDTO> page;
        if (listModelName != null) {
            page = administrativeDivisionQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = administrativeDivisionQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /administrative-divisions/count} : count all the administrativeDivisions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/administrative-divisions/count")
    public ResponseEntity<Long> countAdministrativeDivisions(AdministrativeDivisionCriteria criteria) {
        log.debug("REST request to count AdministrativeDivisions by criteria: {}", criteria);
        return ResponseEntity.ok().body(administrativeDivisionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /administrative-divisions/tree : get all the administrativeDivisions for parent is null.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of administrativeDivisions in body
     */
    @GetMapping("/administrative-divisions/tree")
    public ResponseEntity<List<AdministrativeDivisionDTO>> getAllAdministrativeDivisionsofTree(Pageable pageable) {
        log.debug("REST request to get a page of AdministrativeDivisions");
        Page<AdministrativeDivisionDTO> page = administrativeDivisionService.findAllTop(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /administrative-divisions/:id} : get the "id" administrativeDivision.
     *
     * @param id the id of the administrativeDivisionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the administrativeDivisionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/administrative-divisions/{id}")

    public ResponseEntity<AdministrativeDivisionDTO> getAdministrativeDivision(@PathVariable Long id) {
        log.debug("REST request to get AdministrativeDivision : {}", id);
        Optional<AdministrativeDivisionDTO> administrativeDivisionDTO = administrativeDivisionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(administrativeDivisionDTO);
    }
    /**
     * GET  /administrative-divisions/export : export the administrativeDivisions.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the administrativeDivisionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/administrative-divisions/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<AdministrativeDivisionDTO> page = administrativeDivisionService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            AdministrativeDivisionDTO.class, page.getContent());
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
     * POST  /administrative-divisions/import : import the administrativeDivisions from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the administrativeDivisionDTO, or with status 404 (Not Found)
     */
    @PostMapping("/administrative-divisions/import")
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
        List<AdministrativeDivisionDTO> list = ExcelImportUtil.importExcel(savedFile, AdministrativeDivisionDTO.class, params);
        list.forEach(administrativeDivisionService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /administrative-divisions/:id} : delete the "id" administrativeDivision.
     *
     * @param id the id of the administrativeDivisionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/administrative-divisions/{id}")
    public ResponseEntity<Void> deleteAdministrativeDivision(@PathVariable Long id) {
        log.debug("REST request to delete AdministrativeDivision : {}", id);
        administrativeDivisionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /administrative-divisions} : delete all the "ids" AdministrativeDivisions.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/administrative-divisions")
    public ResponseEntity<Void> deleteAdministrativeDivisionsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete AdministrativeDivisions : {}", ids);
        if (ids != null) {
            ids.forEach(administrativeDivisionService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }


    /**
     * {@code PUT  /administrative-divisions/specified-fields} : Updates an existing administrativeDivision by specified fields.
     *
     * @param administrativeDivisionDTOAndSpecifiedFields the administrativeDivisionDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrativeDivisionDTO,
     * or with status {@code 400 (Bad Request)} if the administrativeDivisionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the administrativeDivisionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/administrative-divisions/specified-fields")
    public ResponseEntity<AdministrativeDivisionDTO> updateAdministrativeDivisionBySpecifiedFields(@RequestBody AdministrativeDivisionDTOAndSpecifiedFields administrativeDivisionDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update AdministrativeDivision : {}", administrativeDivisionDTOAndSpecifiedFields);
        AdministrativeDivisionDTO administrativeDivisionDTO = administrativeDivisionDTOAndSpecifiedFields.getAdministrativeDivision();
        Set<String> specifiedFields = administrativeDivisionDTOAndSpecifiedFields.getSpecifiedFields();
        if (administrativeDivisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdministrativeDivisionDTO result = administrativeDivisionService.updateBySpecifiedFields(administrativeDivisionDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrativeDivisionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /administrative-divisions/specified-field} : Updates an existing administrativeDivision by specified field.
     *
     * @param administrativeDivisionDTOAndSpecifiedFields the administrativeDivisionDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrativeDivisionDTO,
     * or with status {@code 400 (Bad Request)} if the administrativeDivisionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the administrativeDivisionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/administrative-divisions/specified-field")
    public ResponseEntity<AdministrativeDivisionDTO> updateAdministrativeDivisionBySpecifiedField(@RequestBody AdministrativeDivisionDTOAndSpecifiedFields administrativeDivisionDTOAndSpecifiedFields, AdministrativeDivisionCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update AdministrativeDivision : {}", administrativeDivisionDTOAndSpecifiedFields);
        AdministrativeDivisionDTO administrativeDivisionDTO = administrativeDivisionDTOAndSpecifiedFields.getAdministrativeDivision();
        String fieldName = administrativeDivisionDTOAndSpecifiedFields.getSpecifiedField();
        if (administrativeDivisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdministrativeDivisionDTO result = administrativeDivisionService.updateBySpecifiedField(administrativeDivisionDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class AdministrativeDivisionDTOAndSpecifiedFields {
        private AdministrativeDivisionDTO administrativeDivision;
        private Set<String> specifiedFields;
        private String specifiedField;

        private AdministrativeDivisionDTO getAdministrativeDivision() {
            return administrativeDivision;
        }

        private void setAdministrativeDivision(AdministrativeDivisionDTO administrativeDivision) {
            this.administrativeDivision = administrativeDivision;
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
