package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.ProcessFormConfigService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.ProcessFormConfigDTO;
import com.aidriveall.cms.service.dto.ProcessFormConfigCriteria;
import com.aidriveall.cms.service.ProcessFormConfigQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.ProcessFormConfig}.
 */
@RestController
@RequestMapping("/api")
public class ProcessFormConfigResource {

    private final Logger log = LoggerFactory.getLogger(ProcessFormConfigResource.class);

    private static final String ENTITY_NAME = "workflowProcessFormConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessFormConfigService processFormConfigService;

    private final ProcessFormConfigQueryService processFormConfigQueryService;

    public ProcessFormConfigResource(ProcessFormConfigService processFormConfigService, ProcessFormConfigQueryService processFormConfigQueryService) {
        this.processFormConfigService = processFormConfigService;
        this.processFormConfigQueryService = processFormConfigQueryService;
    }

    /**
     * {@code POST  /process-form-configs} : Create a new processFormConfig.
     *
     * @param processFormConfigDTO the processFormConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processFormConfigDTO, or with status {@code 400 (Bad Request)} if the processFormConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-form-configs")
    public ResponseEntity<ProcessFormConfigDTO> createProcessFormConfig(@RequestBody ProcessFormConfigDTO processFormConfigDTO) throws URISyntaxException {
        log.debug("REST request to save ProcessFormConfig : {}", processFormConfigDTO);
        if (processFormConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new processFormConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessFormConfigDTO result = processFormConfigService.save(processFormConfigDTO);
        return ResponseEntity.created(new URI("/api/process-form-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-form-configs} : Updates an existing processFormConfig.
     *
     * @param processFormConfigDTO the processFormConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processFormConfigDTO,
     * or with status {@code 400 (Bad Request)} if the processFormConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processFormConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-form-configs")
    public ResponseEntity<ProcessFormConfigDTO> updateProcessFormConfig(@RequestBody ProcessFormConfigDTO processFormConfigDTO) throws URISyntaxException {
        log.debug("REST request to update ProcessFormConfig : {}", processFormConfigDTO);
        if (processFormConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessFormConfigDTO result = processFormConfigService.save(processFormConfigDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processFormConfigDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /process-form-configs} : get all the processFormConfigs.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processFormConfigs in body.
     */
    @GetMapping("/process-form-configs")
    public ResponseEntity<List<ProcessFormConfigDTO>> getAllProcessFormConfigs(ProcessFormConfigCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get ProcessFormConfigs by criteria: {}", criteria);
        Page<ProcessFormConfigDTO> page;
        if (listModelName != null) {
            page = processFormConfigQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = processFormConfigQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /process-form-configs/count} : count all the processFormConfigs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/process-form-configs/count")
    public ResponseEntity<Long> countProcessFormConfigs(ProcessFormConfigCriteria criteria) {
        log.debug("REST request to count ProcessFormConfigs by criteria: {}", criteria);
        return ResponseEntity.ok().body(processFormConfigQueryService.countByCriteria(criteria));
    }


    /**
     * {@code GET  /process-form-configs/:id} : get the "id" processFormConfig.
     *
     * @param id the id of the processFormConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processFormConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-form-configs/{id}")

    public ResponseEntity<ProcessFormConfigDTO> getProcessFormConfig(@PathVariable Long id) {
        log.debug("REST request to get ProcessFormConfig : {}", id);
        Optional<ProcessFormConfigDTO> processFormConfigDTO = processFormConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processFormConfigDTO);
    }
    /**
     * GET  /process-form-configs/export : export the processFormConfigs.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the processFormConfigDTO, or with status 404 (Not Found)
     */
    @GetMapping("/process-form-configs/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<ProcessFormConfigDTO> page = processFormConfigService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            ProcessFormConfigDTO.class, page.getContent());
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
     * POST  /process-form-configs/import : import the processFormConfigs from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the processFormConfigDTO, or with status 404 (Not Found)
     */
    @PostMapping("/process-form-configs/import")
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
        List<ProcessFormConfigDTO> list = ExcelImportUtil.importExcel(savedFile, ProcessFormConfigDTO.class, params);
        list.forEach(processFormConfigService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /process-form-configs/:id} : delete the "id" processFormConfig.
     *
     * @param id the id of the processFormConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-form-configs/{id}")
    public ResponseEntity<Void> deleteProcessFormConfig(@PathVariable Long id) {
        log.debug("REST request to delete ProcessFormConfig : {}", id);
        processFormConfigService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /process-form-configs} : delete all the "ids" ProcessFormConfigs.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/process-form-configs")
    public ResponseEntity<Void> deleteProcessFormConfigsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete ProcessFormConfigs : {}", ids);
        if (ids != null) {
            ids.forEach(processFormConfigService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }


    /**
     * {@code PUT  /process-form-configs/specified-fields} : Updates an existing processFormConfig by specified fields.
     *
     * @param processFormConfigDTOAndSpecifiedFields the processFormConfigDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processFormConfigDTO,
     * or with status {@code 400 (Bad Request)} if the processFormConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processFormConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-form-configs/specified-fields")
    public ResponseEntity<ProcessFormConfigDTO> updateProcessFormConfigBySpecifiedFields(@RequestBody ProcessFormConfigDTOAndSpecifiedFields processFormConfigDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update ProcessFormConfig : {}", processFormConfigDTOAndSpecifiedFields);
        ProcessFormConfigDTO processFormConfigDTO = processFormConfigDTOAndSpecifiedFields.getProcessFormConfig();
        Set<String> specifiedFields = processFormConfigDTOAndSpecifiedFields.getSpecifiedFields();
        if (processFormConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessFormConfigDTO result = processFormConfigService.updateBySpecifiedFields(processFormConfigDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processFormConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-form-configs/specified-field} : Updates an existing processFormConfig by specified field.
     *
     * @param processFormConfigDTOAndSpecifiedFields the processFormConfigDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processFormConfigDTO,
     * or with status {@code 400 (Bad Request)} if the processFormConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processFormConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-form-configs/specified-field")
    public ResponseEntity<ProcessFormConfigDTO> updateProcessFormConfigBySpecifiedField(@RequestBody ProcessFormConfigDTOAndSpecifiedFields processFormConfigDTOAndSpecifiedFields, ProcessFormConfigCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update ProcessFormConfig : {}", processFormConfigDTOAndSpecifiedFields);
        ProcessFormConfigDTO processFormConfigDTO = processFormConfigDTOAndSpecifiedFields.getProcessFormConfig();
        String fieldName = processFormConfigDTOAndSpecifiedFields.getSpecifiedField();
        if (processFormConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcessFormConfigDTO result = processFormConfigService.updateBySpecifiedField(processFormConfigDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class ProcessFormConfigDTOAndSpecifiedFields {
        private ProcessFormConfigDTO processFormConfig;
        private Set<String> specifiedFields;
        private String specifiedField;

        private ProcessFormConfigDTO getProcessFormConfig() {
            return processFormConfig;
        }

        private void setProcessFormConfig(ProcessFormConfigDTO processFormConfig) {
            this.processFormConfig = processFormConfig;
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
