package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.UploadImageService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.UploadImageDTO;
import com.aidriveall.cms.service.dto.UploadImageCriteria;
import com.aidriveall.cms.service.UploadImageQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.UploadImage}.
 */
@RestController
@RequestMapping("/api")
public class UploadImageResource {

    private final Logger log = LoggerFactory.getLogger(UploadImageResource.class);

    private static final String ENTITY_NAME = "filesUploadImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UploadImageService uploadImageService;

    private final UploadImageQueryService uploadImageQueryService;

    public UploadImageResource(UploadImageService uploadImageService, UploadImageQueryService uploadImageQueryService) {
        this.uploadImageService = uploadImageService;
        this.uploadImageQueryService = uploadImageQueryService;
    }

    /**
     * {@code POST  /upload-images} : Create a new uploadImage.
     *
     * @param uploadImageDTO the uploadImageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uploadImageDTO, or with status {@code 400 (Bad Request)} if the uploadImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/upload-images")
    public ResponseEntity<UploadImageDTO> createUploadImage(@RequestBody UploadImageDTO uploadImageDTO) throws URISyntaxException {
        log.debug("REST request to save UploadImage : {}", uploadImageDTO);
        if (uploadImageDTO.getId() != null) {
            throw new BadRequestAlertException("A new uploadImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UploadImageDTO result = uploadImageService.save(uploadImageDTO);
        return ResponseEntity.created(new URI("/api/upload-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /upload-images} : Updates an existing uploadImage.
     *
     * @param uploadImageDTO the uploadImageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uploadImageDTO,
     * or with status {@code 400 (Bad Request)} if the uploadImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uploadImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/upload-images")
    public ResponseEntity<UploadImageDTO> updateUploadImage(@RequestBody UploadImageDTO uploadImageDTO) throws URISyntaxException {
        log.debug("REST request to update UploadImage : {}", uploadImageDTO);
        if (uploadImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UploadImageDTO result = uploadImageService.save(uploadImageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uploadImageDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /upload-images} : get all the uploadImages.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uploadImages in body.
     */
    @GetMapping("/upload-images")
    public ResponseEntity<List<UploadImageDTO>> getAllUploadImages(UploadImageCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get UploadImages by criteria: {}", criteria);
        Page<UploadImageDTO> page;
        if (listModelName != null) {
            page = uploadImageQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = uploadImageQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /upload-images/count} : count all the uploadImages.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/upload-images/count")
    public ResponseEntity<Long> countUploadImages(UploadImageCriteria criteria) {
        log.debug("REST request to count UploadImages by criteria: {}", criteria);
        return ResponseEntity.ok().body(uploadImageQueryService.countByCriteria(criteria));
    }


    /**
     * {@code GET  /upload-images/:id} : get the "id" uploadImage.
     *
     * @param id the id of the uploadImageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uploadImageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/upload-images/{id}")

    public ResponseEntity<UploadImageDTO> getUploadImage(@PathVariable Long id) {
        log.debug("REST request to get UploadImage : {}", id);
        Optional<UploadImageDTO> uploadImageDTO = uploadImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uploadImageDTO);
    }
    /**
     * GET  /upload-images/export : export the uploadImages.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the uploadImageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/upload-images/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<UploadImageDTO> page = uploadImageService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            UploadImageDTO.class, page.getContent());
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
     * POST  /upload-images/import : import the uploadImages from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the uploadImageDTO, or with status 404 (Not Found)
     */
    @PostMapping("/upload-images/import")
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
        List<UploadImageDTO> list = ExcelImportUtil.importExcel(savedFile, UploadImageDTO.class, params);
        list.forEach(uploadImageService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /upload-images/:id} : delete the "id" uploadImage.
     *
     * @param id the id of the uploadImageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/upload-images/{id}")
    public ResponseEntity<Void> deleteUploadImage(@PathVariable Long id) {
        log.debug("REST request to delete UploadImage : {}", id);
        uploadImageService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /upload-images} : delete all the "ids" UploadImages.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/upload-images")
    public ResponseEntity<Void> deleteUploadImagesByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete UploadImages : {}", ids);
        if (ids != null) {
            ids.forEach(uploadImageService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }

    @GetMapping("/upload-images/user/current-user")
    public ResponseEntity<List<UploadImageDTO>> findByUserIsCurrentUser() {
        log.debug("REST request to get UploadImage for current user. ");
        List<UploadImageDTO> result = uploadImageService.findByUserIsCurrentUser();
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /upload-images/specified-fields} : Updates an existing uploadImage by specified fields.
     *
     * @param uploadImageDTOAndSpecifiedFields the uploadImageDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uploadImageDTO,
     * or with status {@code 400 (Bad Request)} if the uploadImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uploadImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/upload-images/specified-fields")
    public ResponseEntity<UploadImageDTO> updateUploadImageBySpecifiedFields(@RequestBody UploadImageDTOAndSpecifiedFields uploadImageDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update UploadImage : {}", uploadImageDTOAndSpecifiedFields);
        UploadImageDTO uploadImageDTO = uploadImageDTOAndSpecifiedFields.getUploadImage();
        Set<String> specifiedFields = uploadImageDTOAndSpecifiedFields.getSpecifiedFields();
        if (uploadImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UploadImageDTO result = uploadImageService.updateBySpecifiedFields(uploadImageDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uploadImageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /upload-images/specified-field} : Updates an existing uploadImage by specified field.
     *
     * @param uploadImageDTOAndSpecifiedFields the uploadImageDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uploadImageDTO,
     * or with status {@code 400 (Bad Request)} if the uploadImageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uploadImageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/upload-images/specified-field")
    public ResponseEntity<UploadImageDTO> updateUploadImageBySpecifiedField(@RequestBody UploadImageDTOAndSpecifiedFields uploadImageDTOAndSpecifiedFields, UploadImageCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update UploadImage : {}", uploadImageDTOAndSpecifiedFields);
        UploadImageDTO uploadImageDTO = uploadImageDTOAndSpecifiedFields.getUploadImage();
        String fieldName = uploadImageDTOAndSpecifiedFields.getSpecifiedField();
        if (uploadImageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UploadImageDTO result = uploadImageService.updateBySpecifiedField(uploadImageDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class UploadImageDTOAndSpecifiedFields {
        private UploadImageDTO uploadImage;
        private Set<String> specifiedFields;
        private String specifiedField;

        private UploadImageDTO getUploadImage() {
            return uploadImage;
        }

        private void setUploadImage(UploadImageDTO uploadImage) {
            this.uploadImage = uploadImage;
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
