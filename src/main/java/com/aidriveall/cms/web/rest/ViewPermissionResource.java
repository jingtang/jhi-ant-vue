package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.ViewPermissionService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.ViewPermissionDTO;
import com.aidriveall.cms.service.dto.ViewPermissionCriteria;
import com.aidriveall.cms.service.ViewPermissionQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.ViewPermission}.
 */
@RestController
@RequestMapping("/api")
public class ViewPermissionResource {

    private final Logger log = LoggerFactory.getLogger(ViewPermissionResource.class);

    private static final String ENTITY_NAME = "systemViewPermission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ViewPermissionService viewPermissionService;

    private final ViewPermissionQueryService viewPermissionQueryService;

    public ViewPermissionResource(ViewPermissionService viewPermissionService, ViewPermissionQueryService viewPermissionQueryService) {
        this.viewPermissionService = viewPermissionService;
        this.viewPermissionQueryService = viewPermissionQueryService;
    }

    /**
     * {@code POST  /view-permissions} : Create a new viewPermission.
     *
     * @param viewPermissionDTO the viewPermissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new viewPermissionDTO, or with status {@code 400 (Bad Request)} if the viewPermission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/view-permissions")
    public ResponseEntity<ViewPermissionDTO> createViewPermission(@RequestBody ViewPermissionDTO viewPermissionDTO) throws URISyntaxException {
        log.debug("REST request to save ViewPermission : {}", viewPermissionDTO);
        if (viewPermissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new viewPermission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ViewPermissionDTO result = viewPermissionService.save(viewPermissionDTO);
        return ResponseEntity.created(new URI("/api/view-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /view-permissions} : Updates an existing viewPermission.
     *
     * @param viewPermissionDTO the viewPermissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viewPermissionDTO,
     * or with status {@code 400 (Bad Request)} if the viewPermissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viewPermissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/view-permissions")
    public ResponseEntity<ViewPermissionDTO> updateViewPermission(@RequestBody ViewPermissionDTO viewPermissionDTO) throws URISyntaxException {
        log.debug("REST request to update ViewPermission : {}", viewPermissionDTO);
        if (viewPermissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ViewPermissionDTO result = viewPermissionService.save(viewPermissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viewPermissionDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /view-permissions} : get all the viewPermissions.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viewPermissions in body.
     */
    @GetMapping("/view-permissions")
    public ResponseEntity<List<ViewPermissionDTO>> getAllViewPermissions(ViewPermissionCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get ViewPermissions by criteria: {}", criteria);
        Page<ViewPermissionDTO> page;
        if (listModelName != null) {
            page = viewPermissionQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = viewPermissionQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /view-permissions/count} : count all the viewPermissions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/view-permissions/count")
    public ResponseEntity<Long> countViewPermissions(ViewPermissionCriteria criteria) {
        log.debug("REST request to count ViewPermissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(viewPermissionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /view-permissions/tree : get all the viewPermissions for parent is null.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of viewPermissions in body
     */
    @GetMapping("/view-permissions/tree")
    public ResponseEntity<List<ViewPermissionDTO>> getAllViewPermissionsofTree(Pageable pageable) {
        log.debug("REST request to get a page of ViewPermissions");
        Page<ViewPermissionDTO> page = viewPermissionService.findAllTop(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /view-permissions/:id} : get the "id" viewPermission.
     *
     * @param id the id of the viewPermissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viewPermissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/view-permissions/{id}")

    public ResponseEntity<ViewPermissionDTO> getViewPermission(@PathVariable Long id) {
        log.debug("REST request to get ViewPermission : {}", id);
        Optional<ViewPermissionDTO> viewPermissionDTO = viewPermissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(viewPermissionDTO);
    }
    /**
     * GET  /view-permissions/export : export the viewPermissions.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the viewPermissionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/view-permissions/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<ViewPermissionDTO> page = viewPermissionService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            ViewPermissionDTO.class, page.getContent());
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
     * POST  /view-permissions/import : import the viewPermissions from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the viewPermissionDTO, or with status 404 (Not Found)
     */
    @PostMapping("/view-permissions/import")
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
        List<ViewPermissionDTO> list = ExcelImportUtil.importExcel(savedFile, ViewPermissionDTO.class, params);
        list.forEach(viewPermissionService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /view-permissions/:id} : delete the "id" viewPermission.
     *
     * @param id the id of the viewPermissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/view-permissions/{id}")
    public ResponseEntity<Void> deleteViewPermission(@PathVariable Long id) {
        log.debug("REST request to delete ViewPermission : {}", id);
        viewPermissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /view-permissions} : delete all the "ids" ViewPermissions.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/view-permissions")
    public ResponseEntity<Void> deleteViewPermissionsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete ViewPermissions : {}", ids);
        if (ids != null) {
            ids.forEach(viewPermissionService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }


    /**
     * {@code PUT  /view-permissions/specified-fields} : Updates an existing viewPermission by specified fields.
     *
     * @param viewPermissionDTOAndSpecifiedFields the viewPermissionDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viewPermissionDTO,
     * or with status {@code 400 (Bad Request)} if the viewPermissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viewPermissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/view-permissions/specified-fields")
    public ResponseEntity<ViewPermissionDTO> updateViewPermissionBySpecifiedFields(@RequestBody ViewPermissionDTOAndSpecifiedFields viewPermissionDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update ViewPermission : {}", viewPermissionDTOAndSpecifiedFields);
        ViewPermissionDTO viewPermissionDTO = viewPermissionDTOAndSpecifiedFields.getViewPermission();
        Set<String> specifiedFields = viewPermissionDTOAndSpecifiedFields.getSpecifiedFields();
        if (viewPermissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ViewPermissionDTO result = viewPermissionService.updateBySpecifiedFields(viewPermissionDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viewPermissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /view-permissions/specified-field} : Updates an existing viewPermission by specified field.
     *
     * @param viewPermissionDTOAndSpecifiedFields the viewPermissionDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viewPermissionDTO,
     * or with status {@code 400 (Bad Request)} if the viewPermissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viewPermissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/view-permissions/specified-field")
    public ResponseEntity<ViewPermissionDTO> updateViewPermissionBySpecifiedField(@RequestBody ViewPermissionDTOAndSpecifiedFields viewPermissionDTOAndSpecifiedFields, ViewPermissionCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update ViewPermission : {}", viewPermissionDTOAndSpecifiedFields);
        ViewPermissionDTO viewPermissionDTO = viewPermissionDTOAndSpecifiedFields.getViewPermission();
        String fieldName = viewPermissionDTOAndSpecifiedFields.getSpecifiedField();
        if (viewPermissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ViewPermissionDTO result = viewPermissionService.updateBySpecifiedField(viewPermissionDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class ViewPermissionDTOAndSpecifiedFields {
        private ViewPermissionDTO viewPermission;
        private Set<String> specifiedFields;
        private String specifiedField;

        private ViewPermissionDTO getViewPermission() {
            return viewPermission;
        }

        private void setViewPermission(ViewPermissionDTO viewPermission) {
            this.viewPermission = viewPermission;
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
