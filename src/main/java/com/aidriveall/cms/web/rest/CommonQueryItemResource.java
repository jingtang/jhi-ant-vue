package com.aidriveall.cms.web.rest;

import cn.hutool.core.bean.BeanUtil;
import com.aidriveall.cms.service.CommonQueryItemService;
import com.aidriveall.cms.web.rest.errors.BadRequestAlertException;
import com.aidriveall.cms.service.dto.CommonQueryItemDTO;
import com.aidriveall.cms.service.dto.CommonQueryItemCriteria;
import com.aidriveall.cms.service.CommonQueryItemQueryService;

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
 * REST controller for managing {@link com.aidriveall.cms.domain.CommonQueryItem}.
 */
@RestController
@RequestMapping("/api")
public class CommonQueryItemResource {

    private final Logger log = LoggerFactory.getLogger(CommonQueryItemResource.class);

    private static final String ENTITY_NAME = "commonQueryCommonQueryItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommonQueryItemService commonQueryItemService;

    private final CommonQueryItemQueryService commonQueryItemQueryService;

    public CommonQueryItemResource(CommonQueryItemService commonQueryItemService, CommonQueryItemQueryService commonQueryItemQueryService) {
        this.commonQueryItemService = commonQueryItemService;
        this.commonQueryItemQueryService = commonQueryItemQueryService;
    }

    /**
     * {@code POST  /common-query-items} : Create a new commonQueryItem.
     *
     * @param commonQueryItemDTO the commonQueryItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commonQueryItemDTO, or with status {@code 400 (Bad Request)} if the commonQueryItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/common-query-items")
    public ResponseEntity<CommonQueryItemDTO> createCommonQueryItem(@RequestBody CommonQueryItemDTO commonQueryItemDTO) throws URISyntaxException {
        log.debug("REST request to save CommonQueryItem : {}", commonQueryItemDTO);
        if (commonQueryItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new commonQueryItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommonQueryItemDTO result = commonQueryItemService.save(commonQueryItemDTO);
        return ResponseEntity.created(new URI("/api/common-query-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /common-query-items} : Updates an existing commonQueryItem.
     *
     * @param commonQueryItemDTO the commonQueryItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commonQueryItemDTO,
     * or with status {@code 400 (Bad Request)} if the commonQueryItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commonQueryItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/common-query-items")
    public ResponseEntity<CommonQueryItemDTO> updateCommonQueryItem(@RequestBody CommonQueryItemDTO commonQueryItemDTO) throws URISyntaxException {
        log.debug("REST request to update CommonQueryItem : {}", commonQueryItemDTO);
        if (commonQueryItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommonQueryItemDTO result = commonQueryItemService.save(commonQueryItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commonQueryItemDTO.getId().toString()))
            .body(result);
    }
    /**
     * {@code GET  /common-query-items} : get all the commonQueryItems.
     *

     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commonQueryItems in body.
     */
    @GetMapping("/common-query-items")
    public ResponseEntity<List<CommonQueryItemDTO>> getAllCommonQueryItems(CommonQueryItemCriteria criteria, Pageable pageable, @RequestParam(value = "listModelName", required = false) String listModelName) {
        log.debug("REST request to get CommonQueryItems by criteria: {}", criteria);
        Page<CommonQueryItemDTO> page;
        if (listModelName != null) {
            page = commonQueryItemQueryService.selectByCustomEntity(listModelName, criteria,null, pageable);
        } else {
            page = commonQueryItemQueryService.findByCriteria(criteria, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /common-query-items/count} : count all the commonQueryItems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/common-query-items/count")
    public ResponseEntity<Long> countCommonQueryItems(CommonQueryItemCriteria criteria) {
        log.debug("REST request to count CommonQueryItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(commonQueryItemQueryService.countByCriteria(criteria));
    }


    /**
     * {@code GET  /common-query-items/:id} : get the "id" commonQueryItem.
     *
     * @param id the id of the commonQueryItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commonQueryItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/common-query-items/{id}")

    public ResponseEntity<CommonQueryItemDTO> getCommonQueryItem(@PathVariable Long id) {
        log.debug("REST request to get CommonQueryItem : {}", id);
        Optional<CommonQueryItemDTO> commonQueryItemDTO = commonQueryItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commonQueryItemDTO);
    }
    /**
     * GET  /common-query-items/export : export the commonQueryItems.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the commonQueryItemDTO, or with status 404 (Not Found)
     */
    @GetMapping("/common-query-items/export")
    public ResponseEntity<Void> exportToExcel() throws IOException {
        Page<CommonQueryItemDTO> page = commonQueryItemService.findAll(Pageable.unpaged());
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班学生","学生"),
            CommonQueryItemDTO.class, page.getContent());
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
     * POST  /common-query-items/import : import the commonQueryItems from excel file.
     *
     *
     * @return the ResponseEntity with status 200 (OK) and with body the commonQueryItemDTO, or with status 404 (Not Found)
     */
    @PostMapping("/common-query-items/import")
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
        List<CommonQueryItemDTO> list = ExcelImportUtil.importExcel(savedFile, CommonQueryItemDTO.class, params);
        list.forEach(commonQueryItemService::save);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code DELETE  /common-query-items/:id} : delete the "id" commonQueryItem.
     *
     * @param id the id of the commonQueryItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/common-query-items/{id}")
    public ResponseEntity<Void> deleteCommonQueryItem(@PathVariable Long id) {
        log.debug("REST request to delete CommonQueryItem : {}", id);
        commonQueryItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
    * {@code DELETE  /common-query-items} : delete all the "ids" CommonQueryItems.
    *
    * @param ids the ids of the articleDTO to delete.
    * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    */
    @DeleteMapping("/common-query-items")
    public ResponseEntity<Void> deleteCommonQueryItemsByIds(@RequestParam("ids") ArrayList<Long> ids) {
        log.debug("REST request to delete CommonQueryItems : {}", ids);
        if (ids != null) {
            ids.forEach(commonQueryItemService::delete);
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, (ids != null ? ids.toString() : "NoIds"))).build();
    }


    /**
     * {@code PUT  /common-query-items/specified-fields} : Updates an existing commonQueryItem by specified fields.
     *
     * @param commonQueryItemDTOAndSpecifiedFields the commonQueryItemDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commonQueryItemDTO,
     * or with status {@code 400 (Bad Request)} if the commonQueryItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commonQueryItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/common-query-items/specified-fields")
    public ResponseEntity<CommonQueryItemDTO> updateCommonQueryItemBySpecifiedFields(@RequestBody CommonQueryItemDTOAndSpecifiedFields commonQueryItemDTOAndSpecifiedFields) throws URISyntaxException {
        log.debug("REST request to update CommonQueryItem : {}", commonQueryItemDTOAndSpecifiedFields);
        CommonQueryItemDTO commonQueryItemDTO = commonQueryItemDTOAndSpecifiedFields.getCommonQueryItem();
        Set<String> specifiedFields = commonQueryItemDTOAndSpecifiedFields.getSpecifiedFields();
        if (commonQueryItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommonQueryItemDTO result = commonQueryItemService.updateBySpecifiedFields(commonQueryItemDTO,specifiedFields);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commonQueryItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /common-query-items/specified-field} : Updates an existing commonQueryItem by specified field.
     *
     * @param commonQueryItemDTOAndSpecifiedFields the commonQueryItemDTO and specifiedFields to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commonQueryItemDTO,
     * or with status {@code 400 (Bad Request)} if the commonQueryItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commonQueryItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/common-query-items/specified-field")
    public ResponseEntity<CommonQueryItemDTO> updateCommonQueryItemBySpecifiedField(@RequestBody CommonQueryItemDTOAndSpecifiedFields commonQueryItemDTOAndSpecifiedFields, CommonQueryItemCriteria criteria) throws URISyntaxException {
        log.debug("REST request to update CommonQueryItem : {}", commonQueryItemDTOAndSpecifiedFields);
        CommonQueryItemDTO commonQueryItemDTO = commonQueryItemDTOAndSpecifiedFields.getCommonQueryItem();
        String fieldName = commonQueryItemDTOAndSpecifiedFields.getSpecifiedField();
        if (commonQueryItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommonQueryItemDTO result = commonQueryItemService.updateBySpecifiedField(commonQueryItemDTO, fieldName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    // jhipster-needle-rest-resource-add-api - JHipster will add getters and setters here, do not remove

    private static class CommonQueryItemDTOAndSpecifiedFields {
        private CommonQueryItemDTO commonQueryItem;
        private Set<String> specifiedFields;
        private String specifiedField;

        private CommonQueryItemDTO getCommonQueryItem() {
            return commonQueryItem;
        }

        private void setCommonQueryItem(CommonQueryItemDTO commonQueryItem) {
            this.commonQueryItem = commonQueryItem;
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
