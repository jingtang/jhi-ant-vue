package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.LeaveService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.LeaveDTO;
import com.aidriveall.cms.service.dto.LeaveCriteria;
import com.aidriveall.cms.service.LeaveQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.Leave}.
 */
@RestController
@RequestMapping("/api")
public class LeaveResource {

    private final Logger log = LoggerFactory.getLogger(LeaveResource.class);

    private static final String ENTITY_NAME = "workflowLeave";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveService leaveService;

    private final LeaveQueryService leaveQueryService;

    public LeaveResource(LeaveService leaveService, LeaveQueryService leaveQueryService) {
        this.leaveService = leaveService;
        this.leaveQueryService = leaveQueryService;
    }

    /**
     * {@code POST  /leaves} : Create a new leave.
     *
     * @param leaveDTO the leaveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveDTO, or with status {@code 400 (Bad Request)} if the leave has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leaves")
    public ResponseEntity<LeaveDTO> createLeave(@RequestBody LeaveDTO leaveDTO) throws URISyntaxException {
        log.debug("REST request to save Leave : {}", leaveDTO);
        if (leaveDTO.getId() != null) {
            throw new BadRequestAlertException("A new leave cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveDTO result = leaveService.save(leaveDTO);
        return ResponseEntity.created(new URI("/api/leaves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leaves} : Updates an existing leave.
     *
     * @param leaveDTO the leaveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveDTO,
     * or with status {@code 400 (Bad Request)} if the leaveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leaves")
    public ResponseEntity<LeaveDTO> updateLeave(@RequestBody LeaveDTO leaveDTO) throws URISyntaxException {
        log.debug("REST request to update Leave : {}", leaveDTO);
        if (leaveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveDTO result = leaveService.save(leaveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leaveDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /leaves} : get all the leaves.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaves in body.
     */
    @GetMapping("/leaves")
    public ResponseEntity<List<LeaveDTO>> getAllLeaves(LeaveCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get Leaves by criteria: {}", criteria);
        Page<LeaveDTO> page;
        if (listModelName != null) {
            page = leaveQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = leaveQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /leaves/count} : count all the leaves.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/leaves/count")
    public ResponseEntity<Long> countLeaves(LeaveCriteria criteria) {
        log.debug("REST request to count Leaves by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveQueryService.countByCriteria(criteria));
    }


    /**
     * {@code GET  /leaves/:id} : get the "id" leave.
     *
     * @param id the id of the leaveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leaves/{id}")

    public ResponseEntity<LeaveDTO> getLeave(@PathVariable Long id) {
        log.debug("REST request to get Leave : {}", id);
        Optional<LeaveDTO> leaveDTO = leaveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveDTO);
    }
    /**
     * GET  /leaves/export : export the leaves.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the leaveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/leaves/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<LeaveDTO> page = leaveService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            LeaveDTO.class, page.getContent());
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
     * POST  /leaves/import : import the leaves from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the leaveDTO, or with status 404 (Not Found)
     */
    @PostMapping("/leaves/import")
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
        List<LeaveDTO> list = ExcelImportUtil.importExcel(savedFile, LeaveDTO.class, params);
        list.forEach(leaveService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /leaves/:id} : delete the "id" leave.
     *
     * @param id the id of the leaveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leaves/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Long id) {
        log.debug("REST request to delete Leave : {}", id);
        leaveService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /leaves} : delete all the "ids" Leaves.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/leaves")
    public ResponseEntity<Void> deleteLeavesByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete Leaves : {}", ids);
        if (ids != null) {
            ids.forEach(leaveService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }

    @GetMapping("/leaves/creator/current-user")
    public ResponseEntity<List<LeaveDTO>> findByCreatorIsCurrentUser() {
        log.debug("REST request to get Leave for current user. ");
        List<LeaveDTO> result = leaveService.findByCreatorIsCurrentUser();
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /leaves/specified-fields} : Updates an existing leave by specified fields.
     *
     * @param leaveDTOAndSpecifiedFields the leaveDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveDTO,
     * or with status {@code 400 (Bad Request)} if the leaveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leaves/specified-fields")
    public ResponseEntity<LeaveDTO> updateLeaveBySpecifiedFields(@RequestBody LeaveDTOAndSpecifiedFields leaveDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update Leave : {}", leaveDTOAndSpecifiedFields);
        LeaveDTO leaveDTO = leaveDTOAndSpecifiedFields.getLeave();
        Set<String> specifiedFields = leaveDTOAndSpecifiedFields.getSpecifiedFields();
        if (leaveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveDTO result = leaveService.updateBySpecifiedFields(leaveDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leaveDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leaves/specified-field} : Updates an existing leave by specified field.
     *
     * @param leaveDTOAndSpecifiedFields the leaveDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveDTO,
     * or with status {@code 400 (Bad Request)} if the leaveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leaves/specified-field")
    public ResponseEntity<LeaveDTO> updateLeaveBySpecifiedField(@RequestBody LeaveDTOAndSpecifiedFields leaveDTOAndSpecifiedFields, LeaveCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update Leave : {}", leaveDTOAndSpecifiedFields);
        LeaveDTO leaveDTO = leaveDTOAndSpecifiedFields.getLeave();
        String fieldName = leaveDTOAndSpecifiedFields.getSpecifiedField();
        if (leaveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveDTO result = leaveService.updateBySpecifiedField(leaveDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class LeaveDTOAndSpecifiedFields {
        private LeaveDTO leave;
        private Set<String> specifiedFields;
        private String specifiedField;

        private LeaveDTO getLeave() {
            return leave;
        }

        private void setLeave(LeaveDTO leave) {
            this.leave = leave;
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
