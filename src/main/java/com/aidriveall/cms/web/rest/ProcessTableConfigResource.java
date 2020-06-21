package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.ProcessTableConfigService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.ProcessTableConfigDTO;
import com.aidriveall.cms.service.dto.ProcessTableConfigCriteria;
import com.aidriveall.cms.service.ProcessTableConfigQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.ProcessTableConfig}.
 */
@RestController
@RequestMapping("/api")
public class ProcessTableConfigResource {

    private final Logger log = LoggerFactory.getLogger(ProcessTableConfigResource.class);

    private static final String ENTITY_NAME = "workflowProcessTableConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessTableConfigService processTableConfigService;

    private final ProcessTableConfigQueryService processTableConfigQueryService;

    public ProcessTableConfigResource(ProcessTableConfigService processTableConfigService, ProcessTableConfigQueryService processTableConfigQueryService) {
        this.processTableConfigService = processTableConfigService;
        this.processTableConfigQueryService = processTableConfigQueryService;
    }

    /**
     * {@code POST  /process-table-configs} : Create a new processTableConfig.
     *
     * @param processTableConfigDTO the processTableConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processTableConfigDTO, or with status {@code 400 (Bad Request)} if the processTableConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-table-configs")
    public ResponseEntity<ProcessTableConfigDTO> createProcessTableConfig(@RequestBody ProcessTableConfigDTO processTableConfigDTO) throws URISyntaxException {
        log.debug("REST request to save ProcessTableConfig : {}", processTableConfigDTO);
        if (processTableConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new processTableConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessTableConfigDTO result = processTableConfigService.save(processTableConfigDTO);
        return ResponseEntity.created(new URI("/api/process-table-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-table-configs} : Updates an existing processTableConfig.
     *
     * @param processTableConfigDTO the processTableConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processTableConfigDTO,
     * or with status {@code 400 (Bad Request)} if the processTableConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processTableConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-table-configs")
    public ResponseEntity<ProcessTableConfigDTO> updateProcessTableConfig(@RequestBody ProcessTableConfigDTO processTableConfigDTO) throws URISyntaxException {
        log.debug("REST request to update ProcessTableConfig : {}", processTableConfigDTO);
        if (processTableConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessTableConfigDTO result = processTableConfigService.save(processTableConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processTableConfigDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /process-table-configs} : get all the processTableConfigs.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processTableConfigs in body.
     */
    @GetMapping("/process-table-configs")
    public ResponseEntity<List<ProcessTableConfigDTO>> getAllProcessTableConfigs(ProcessTableConfigCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get ProcessTableConfigs by criteria: {}", criteria);
        Page<ProcessTableConfigDTO> page;
        if (listModelName != null) {
            page = processTableConfigQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = processTableConfigQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /process-table-configs/count} : count all the processTableConfigs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/process-table-configs/count")
    public ResponseEntity<Long> countProcessTableConfigs(ProcessTableConfigCriteria criteria) {
        log.debug("REST request to count ProcessTableConfigs by criteria: {}", criteria);
        return ResponseEntity.ok().body(processTableConfigQueryService.countByCriteria(criteria));
    }


    /**
     * {@code GET  /process-table-configs/:id} : get the "id" processTableConfig.
     *
     * @param id the id of the processTableConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processTableConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-table-configs/{id}")

    public ResponseEntity<ProcessTableConfigDTO> getProcessTableConfig(@PathVariable Long id) {
        log.debug("REST request to get ProcessTableConfig : {}", id);
        Optional<ProcessTableConfigDTO> processTableConfigDTO = processTableConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processTableConfigDTO);
    }
    /**
     * GET  /process-table-configs/export : export the processTableConfigs.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the processTableConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/process-table-configs/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<ProcessTableConfigDTO> page = processTableConfigService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            ProcessTableConfigDTO.class, page.getContent());
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
     * POST  /process-table-configs/import : import the processTableConfigs from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the processTableConfigDTO, or with status 404 (Not Found)
     */
    @PostMapping("/process-table-configs/import")
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
        List<ProcessTableConfigDTO> list = ExcelImportUtil.importExcel(savedFile, ProcessTableConfigDTO.class, params);
        list.forEach(processTableConfigService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /process-table-configs/:id} : delete the "id" processTableConfig.
     *
     * @param id the id of the processTableConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-table-configs/{id}")
    public ResponseEntity<Void> deleteProcessTableConfig(@PathVariable Long id) {
        log.debug("REST request to delete ProcessTableConfig : {}", id);
        processTableConfigService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /process-table-configs} : delete all the "ids" ProcessTableConfigs.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/process-table-configs")
    public ResponseEntity<Void> deleteProcessTableConfigsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete ProcessTableConfigs : {}", ids);
        if (ids != null) {
            ids.forEach(processTableConfigService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }

    @GetMapping("/process-table-configs/creator/current-user")
    public ResponseEntity<List<ProcessTableConfigDTO>> findByCreatorIsCurrentUser() {
        log.debug("REST request to get ProcessTableConfig for current user. ");
        List<ProcessTableConfigDTO> result = processTableConfigService.findByCreatorIsCurrentUser();
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /process-table-configs/specified-fields} : Updates an existing processTableConfig by specified fields.
     *
     * @param processTableConfigDTOAndSpecifiedFields the processTableConfigDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processTableConfigDTO,
     * or with status {@code 400 (Bad Request)} if the processTableConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processTableConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-table-configs/specified-fields")
    public ResponseEntity<ProcessTableConfigDTO> updateProcessTableConfigBySpecifiedFields(@RequestBody ProcessTableConfigDTOAndSpecifiedFields processTableConfigDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update ProcessTableConfig : {}", processTableConfigDTOAndSpecifiedFields);
        ProcessTableConfigDTO processTableConfigDTO = processTableConfigDTOAndSpecifiedFields.getProcessTableConfig();
        Set<String> specifiedFields = processTableConfigDTOAndSpecifiedFields.getSpecifiedFields();
        if (processTableConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessTableConfigDTO result = processTableConfigService.updateBySpecifiedFields(processTableConfigDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processTableConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-table-configs/specified-field} : Updates an existing processTableConfig by specified field.
     *
     * @param processTableConfigDTOAndSpecifiedFields the processTableConfigDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processTableConfigDTO,
     * or with status {@code 400 (Bad Request)} if the processTableConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processTableConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-table-configs/specified-field")
    public ResponseEntity<ProcessTableConfigDTO> updateProcessTableConfigBySpecifiedField(@RequestBody ProcessTableConfigDTOAndSpecifiedFields processTableConfigDTOAndSpecifiedFields, ProcessTableConfigCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update ProcessTableConfig : {}", processTableConfigDTOAndSpecifiedFields);
        ProcessTableConfigDTO processTableConfigDTO = processTableConfigDTOAndSpecifiedFields.getProcessTableConfig();
        String fieldName = processTableConfigDTOAndSpecifiedFields.getSpecifiedField();
        if (processTableConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessTableConfigDTO result = processTableConfigService.updateBySpecifiedField(processTableConfigDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class ProcessTableConfigDTOAndSpecifiedFields {
        private ProcessTableConfigDTO processTableConfig;
        private Set<String> specifiedFields;
        private String specifiedField;

        private ProcessTableConfigDTO getProcessTableConfig() {
            return processTableConfig;
        }

        private void setProcessTableConfig(ProcessTableConfigDTO processTableConfig) {
            this.processTableConfig = processTableConfig;
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
