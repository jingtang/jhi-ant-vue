package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.GpsInfoService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.GpsInfoDTO;
import com.aidriveall.cms.service.dto.GpsInfoCriteria;
import com.aidriveall.cms.service.GpsInfoQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.GpsInfo}.
 */
@RestController
@RequestMapping("/api")
public class GpsInfoResource {

    private final Logger log = LoggerFactory.getLogger(GpsInfoResource.class);

    private static final String ENTITY_NAME = "settingsGpsInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GpsInfoService gpsInfoService;

    private final GpsInfoQueryService gpsInfoQueryService;

    public GpsInfoResource(GpsInfoService gpsInfoService, GpsInfoQueryService gpsInfoQueryService) {
        this.gpsInfoService = gpsInfoService;
        this.gpsInfoQueryService = gpsInfoQueryService;
    }

    /**
     * {@code POST  /gps-infos} : Create a new gpsInfo.
     *
     * @param gpsInfoDTO the gpsInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gpsInfoDTO, or with status {@code 400 (Bad Request)} if the gpsInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gps-infos")
    public ResponseEntity<GpsInfoDTO> createGpsInfo(@RequestBody GpsInfoDTO gpsInfoDTO) throws URISyntaxException {
        log.debug("REST request to save GpsInfo : {}", gpsInfoDTO);
        if (gpsInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new gpsInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GpsInfoDTO result = gpsInfoService.save(gpsInfoDTO);
        return ResponseEntity.created(new URI("/api/gps-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gps-infos} : Updates an existing gpsInfo.
     *
     * @param gpsInfoDTO the gpsInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gpsInfoDTO,
     * or with status {@code 400 (Bad Request)} if the gpsInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gpsInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gps-infos")
    public ResponseEntity<GpsInfoDTO> updateGpsInfo(@RequestBody GpsInfoDTO gpsInfoDTO) throws URISyntaxException {
        log.debug("REST request to update GpsInfo : {}", gpsInfoDTO);
        if (gpsInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GpsInfoDTO result = gpsInfoService.save(gpsInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gpsInfoDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /gps-infos} : get all the gpsInfos.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gpsInfos in body.
     */
    @GetMapping("/gps-infos")
    public ResponseEntity<List<GpsInfoDTO>> getAllGpsInfos(GpsInfoCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get GpsInfos by criteria: {}", criteria);
        Page<GpsInfoDTO> page;
        if (listModelName != null) {
            page = gpsInfoQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = gpsInfoQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /gps-infos/count} : count all the gpsInfos.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/gps-infos/count")
    public ResponseEntity<Long> countGpsInfos(GpsInfoCriteria criteria) {
        log.debug("REST request to count GpsInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(gpsInfoQueryService.countByCriteria(criteria));
    }


    /**
     * {@code GET  /gps-infos/:id} : get the "id" gpsInfo.
     *
     * @param id the id of the gpsInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gpsInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gps-infos/{id}")

    public ResponseEntity<GpsInfoDTO> getGpsInfo(@PathVariable Long id) {
        log.debug("REST request to get GpsInfo : {}", id);
        Optional<GpsInfoDTO> gpsInfoDTO = gpsInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gpsInfoDTO);
    }
    /**
     * GET  /gps-infos/export : export the gpsInfos.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the gpsInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/gps-infos/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<GpsInfoDTO> page = gpsInfoService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            GpsInfoDTO.class, page.getContent());
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
     * POST  /gps-infos/import : import the gpsInfos from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the gpsInfoDTO, or with status 404 (Not Found)
     */
    @PostMapping("/gps-infos/import")
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
        List<GpsInfoDTO> list = ExcelImportUtil.importExcel(savedFile, GpsInfoDTO.class, params);
        list.forEach(gpsInfoService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /gps-infos/:id} : delete the "id" gpsInfo.
     *
     * @param id the id of the gpsInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gps-infos/{id}")
    public ResponseEntity<Void> deleteGpsInfo(@PathVariable Long id) {
        log.debug("REST request to delete GpsInfo : {}", id);
        gpsInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /gps-infos} : delete all the "ids" GpsInfos.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/gps-infos")
    public ResponseEntity<Void> deleteGpsInfosByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete GpsInfos : {}", ids);
        if (ids != null) {
            ids.forEach(gpsInfoService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }


    /**
     * {@code PUT  /gps-infos/specified-fields} : Updates an existing gpsInfo by specified fields.
     *
     * @param gpsInfoDTOAndSpecifiedFields the gpsInfoDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gpsInfoDTO,
     * or with status {@code 400 (Bad Request)} if the gpsInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gpsInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gps-infos/specified-fields")
    public ResponseEntity<GpsInfoDTO> updateGpsInfoBySpecifiedFields(@RequestBody GpsInfoDTOAndSpecifiedFields gpsInfoDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update GpsInfo : {}", gpsInfoDTOAndSpecifiedFields);
        GpsInfoDTO gpsInfoDTO = gpsInfoDTOAndSpecifiedFields.getGpsInfo();
        Set<String> specifiedFields = gpsInfoDTOAndSpecifiedFields.getSpecifiedFields();
        if (gpsInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GpsInfoDTO result = gpsInfoService.updateBySpecifiedFields(gpsInfoDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gpsInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gps-infos/specified-field} : Updates an existing gpsInfo by specified field.
     *
     * @param gpsInfoDTOAndSpecifiedFields the gpsInfoDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gpsInfoDTO,
     * or with status {@code 400 (Bad Request)} if the gpsInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gpsInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gps-infos/specified-field")
    public ResponseEntity<GpsInfoDTO> updateGpsInfoBySpecifiedField(@RequestBody GpsInfoDTOAndSpecifiedFields gpsInfoDTOAndSpecifiedFields, GpsInfoCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update GpsInfo : {}", gpsInfoDTOAndSpecifiedFields);
        GpsInfoDTO gpsInfoDTO = gpsInfoDTOAndSpecifiedFields.getGpsInfo();
        String fieldName = gpsInfoDTOAndSpecifiedFields.getSpecifiedField();
        if (gpsInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GpsInfoDTO result = gpsInfoService.updateBySpecifiedField(gpsInfoDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class GpsInfoDTOAndSpecifiedFields {
        private GpsInfoDTO gpsInfo;
        private Set<String> specifiedFields;
        private String specifiedField;

        private GpsInfoDTO getGpsInfo() {
            return gpsInfo;
        }

        private void setGpsInfo(GpsInfoDTO gpsInfo) {
            this.gpsInfo = gpsInfo;
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
