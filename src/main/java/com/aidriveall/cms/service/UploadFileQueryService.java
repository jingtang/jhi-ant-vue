package com.aidriveall.cms.service;

import java.util.*;
import java.util.stream.Collectors;
import cn.hutool.core.bean.BeanUtil;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;
import io.github.jhipster.service.filter.LongFilter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.query.criteria.internal.path.PluralAttributePath;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.aidriveall.cms.domain.UploadFile;
import com.aidriveall.cms.domain.*; // for static metamodels
import com.aidriveall.cms.repository.CommonTableRepository;
import com.aidriveall.cms.domain.enumeration.RelationshipType;
import com.aidriveall.cms.repository.UploadFileRepository;
import com.aidriveall.cms.service.dto.UploadFileCriteria;
import com.aidriveall.cms.service.dto.UploadFileDTO;
import com.aidriveall.cms.service.mapper.UploadFileMapper;

/**
 * Service for executing complex queries for {@link UploadFile} entities in the database.
 * The main input is a {@link UploadFileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UploadFileDTO} or a {@link Page} of {@link UploadFileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UploadFileQueryService extends QueryService<UploadFile> {

    private final Logger log = LoggerFactory.getLogger(UploadFileQueryService.class);

    private final UploadFileRepository uploadFileRepository;

    private final EntityManager em;

    private final CommonTableRepository commonTableRepository;

    private final UploadFileMapper uploadFileMapper;

    public UploadFileQueryService(UploadFileRepository uploadFileRepository, EntityManager em, CommonTableRepository commonTableRepository, UploadFileMapper uploadFileMapper) {
        this.uploadFileRepository = uploadFileRepository;
        this.em = em;
        this.commonTableRepository = commonTableRepository;
        this.uploadFileMapper = uploadFileMapper;
    }

    /**
     * Return a {@link List} of {@link UploadFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UploadFileDTO> findByCriteria(UploadFileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UploadFile> specification = createSpecification(criteria);
        return uploadFileMapper.toDto(uploadFileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UploadFileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UploadFileDTO> findByCriteria(UploadFileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UploadFile> specification = createSpecification(criteria);
        return uploadFileRepository.findAll(specification, page)
            .map(uploadFileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UploadFileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UploadFile> specification = createSpecification(criteria);
        return uploadFileRepository.count(specification);
    }

    /**
     * Function to convert {@link UploadFileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UploadFile> createSpecification(UploadFileCriteria criteria) {
        Specification<UploadFile> specification = Specification.where(null);
        if (criteria != null) {
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification = specification.or(buildSpecification(new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),UploadFile_.id));
                    specification = specification.or(buildRangeSpecification(
                        (LongFilter)new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),UploadFile_.fileSize)
                    );
                    specification = specification.or(buildRangeSpecification(
                        (LongFilter)new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),UploadFile_.referenceCount)
                    );
                } else {
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),UploadFile_.fullName)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),UploadFile_.name)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),UploadFile_.ext)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),UploadFile_.type)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),UploadFile_.url)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),UploadFile_.path)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),UploadFile_.folder)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),UploadFile_.entityName)
                    );
                }
            } else {
                if (criteria.getId() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getId(), UploadFile_.id));
                }
                if (criteria.getFullName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getFullName(), UploadFile_.fullName));
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), UploadFile_.name));
                }
                if (criteria.getExt() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getExt(), UploadFile_.ext));
                }
                if (criteria.getType() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getType(), UploadFile_.type));
                }
                if (criteria.getUrl() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getUrl(), UploadFile_.url));
                }
                if (criteria.getPath() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getPath(), UploadFile_.path));
                }
                if (criteria.getFolder() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getFolder(), UploadFile_.folder));
                }
                if (criteria.getEntityName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getEntityName(), UploadFile_.entityName));
                }
                if (criteria.getCreateAt() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getCreateAt(), UploadFile_.createAt));
                }
                if (criteria.getFileSize() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getFileSize(), UploadFile_.fileSize));
                }
                if (criteria.getReferenceCount() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getReferenceCount(), UploadFile_.referenceCount));
                }
                if (criteria.getUserId() != null) {
                    specification = specification.and(buildSpecification(criteria.getUserId(),
                        root -> root.join(UploadFile_.user, JoinType.LEFT).get(User_.id)));
                }
            }
        }
        return specification;
    }

    @Transactional
    public boolean updateBySpecifield(String fieldName, Object value, UploadFileCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<UploadFile> q = cb.createCriteriaUpdate(UploadFile.class);
        CriteriaQuery<UploadFile> sq = cb.createQuery(UploadFile.class);
        Root<UploadFile> root = q.from(UploadFile.class);
        q.set(root.get(fieldName), value)
            .where(createSpecification(criteria).toPredicate(root,sq,cb));
        int result = em.createQuery(q).executeUpdate();
        em.flush();
        return result > 0;
    }

    /**
     * 直接转换为dto。maytoone的，直接查询结果。one-to-many和many-to-many后续加载
     * @param entityName 模型名称
     * @param criteria 条件表达式
     * @param predicate 条件
     * @param pageable 分页
     * @return Page<UploadFileDTO>
     */
    @Transactional(readOnly = true)
    public Page<UploadFileDTO> selectByCustomEntity(String entityName, UploadFileCriteria criteria, Predicate predicate, Pageable pageable) {
        List<UploadFileDTO> dataList = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createTupleQuery();
        Root<UploadFile> root = q.from(UploadFile.class);
        if (StringUtils.isEmpty(entityName)) {
            entityName = "UploadFile";
        }
        Optional<CommonTable> oneByEntityName = commonTableRepository.findOneByEntityName(entityName);
        List<String> fields = new ArrayList<>();
        List<CommonTableRelationship> toManyRelationships = new ArrayList<>();
        List<Selection<?>> s = new ArrayList<>();
        if (oneByEntityName.isPresent()) {
            List<CommonTableField> tableFields = oneByEntityName.get().getCommonTableFields().stream()
                .filter(commonTableField -> !commonTableField.isHideInList()).collect(Collectors.toList());
            tableFields.forEach(commonTableField -> fields.add(commonTableField.getEntityFieldName()));
            List<CommonTableRelationship> tableRelationships = oneByEntityName.get().getRelationships().stream()
                .filter(commonTableRelationship -> !commonTableRelationship.isHideInList()).collect(Collectors.toList());

            tableRelationships.forEach(commonTableRelationship -> {
                if (commonTableRelationship.getRelationshipType().equals(RelationshipType.ONE_TO_MANY) || commonTableRelationship.getRelationshipType().equals(RelationshipType.MANY_TO_MANY)) {
                    toManyRelationships.add(commonTableRelationship);
                } else {
                    s.add(root.get(commonTableRelationship.getRelationshipName()).get("id")
                        .alias(commonTableRelationship.getRelationshipName() + "Id"));
                    s.add(root.get(commonTableRelationship.getRelationshipName()).get(commonTableRelationship.getOtherEntityField())
                        .alias(commonTableRelationship.getRelationshipName() + toUpperFirstChar2(commonTableRelationship.getOtherEntityField())));
                }
            });
        }
        s.addAll(fields.stream().map(fieldName -> root.get(fieldName).alias(fieldName)).collect(Collectors.toList()));
        q.multiselect(s);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        if (criteriaPredicate != null) {
            q.where(criteriaPredicate);
        } else if (predicate != null) {
            q.where(predicate);
        }
        q.distinct(true);
        long totalItems = countByCriteria(criteria);
        if (totalItems > 0) {
            if (pageable != null) {
                List<Order> orders = new ArrayList<>();
                pageable.getSort().forEach(
                    order -> orders.add(
                        order.isAscending() ?
                            cb.asc(root.get(order.getProperty())) :
                            cb.desc(root.get(order.getProperty()))));
                q.orderBy(orders);
            }
            TypedQuery<Tuple> query = em.createQuery(q);
            if (pageable != null) {
                pageable.getSort().toList();
                query.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
                query.setMaxResults(pageable.getPageSize());
            }
            List<Tuple> list = query.getResultList();
            for (Tuple tu : list
            ) {
                Map<String, Object> itemmap = new HashMap<>();
                for (TupleElement<?> element : tu.getElements()) {
                    itemmap.put(element.getAlias(), tu.get(element.getAlias()));
                }
                UploadFileDTO uploadFileDTO = BeanUtil.mapToBean(itemmap, UploadFileDTO.class, true);
                toManyRelationships.forEach(commonTableRelationship -> {
                    CriteriaQuery<Tuple> subQuery = cb.createTupleQuery();
                    Class<?> clazz = ((PluralAttributePath<?>)root.get(commonTableRelationship.getRelationshipName())).getAttribute().getElementType().getJavaType();
                    Root<?> manyRoot = subQuery.from(clazz);
                    if (clazz.getSimpleName().equals("UploadFile")) {
                        BeanUtil.setFieldValue(
                            uploadFileDTO,commonTableRelationship.getRelationshipName(),
                            this.selectByCustomEntity(
                                "UploadFile",
                                null,
                                cb.equal(manyRoot.get(commonTableRelationship.getOtherEntityRelationshipName()).get("id"), itemmap.get("id")), null));
                    } else {
                        Selection<Object> idAlias = manyRoot.get("id").alias("id");
                        if (commonTableRelationship.getOtherEntityField() != null) {
                            subQuery.multiselect(idAlias, manyRoot.get(commonTableRelationship.getOtherEntityField()).alias(commonTableRelationship.getOtherEntityField()));
                            subQuery.where(cb.equal(manyRoot.get(commonTableRelationship.getOtherEntityRelationshipName()).get("id"), itemmap.get("id")));
                            Set<Object> subdataList = new LinkedHashSet<>();
                            List<Tuple> sublist = em.createQuery(subQuery).getResultList();
                            for (Tuple stu : sublist) {
                                Map<String, Object> subitemmap = new HashMap<>();
                                for (TupleElement<?> element : stu.getElements()) {
                                    subitemmap.put(element.getAlias(), stu.get(element.getAlias()));
                                }
                                subdataList.add(BeanUtil.mapToBean(subitemmap, clazz, true));
                            }
                            BeanUtil.setFieldValue(uploadFileDTO,commonTableRelationship.getRelationshipName(),subdataList);
                        } else {
                            // todo 暂时不予处理getOtherEntityField为null的情况。
                        }

                    }
                });
                dataList.add(uploadFileDTO);
            }
        }
        return new PageImpl<>(dataList, pageable == null ? Pageable.unpaged() : pageable, totalItems);
    }

    public Map<String, Object> minValue() {
        String sql = "";
        Query query = em.createQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>)query.getSingleResult();
    }
    public Map<String, Object> maxValue() {
        String sql = "";
        Query query = em.createQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>)query.getSingleResult();
    }

    public List<Map<String, Object>> findAllByJpql(String jqpl) {
        Query query = em.createQuery(jqpl);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public Map<String, Object> getByJpql(String jqpl) {
        Query query = em.createQuery(jqpl);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (Map<String, Object>)query.getSingleResult();
    }

    public String toUpperFirstChar2(String string) {
        char[] chars = string.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] -= 32;
            return String.valueOf(chars);
        }
        return string;
    }
}
