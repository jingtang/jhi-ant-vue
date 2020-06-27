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
import io.github.jhipster.service.filter.IntegerFilter;
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

import com.aidriveall.cms.domain.ViewPermission;
import com.aidriveall.cms.domain.*; // for static metamodels
import com.aidriveall.cms.repository.CommonTableRepository;
import com.aidriveall.cms.domain.enumeration.RelationshipType;
import com.aidriveall.cms.repository.ViewPermissionRepository;
import com.aidriveall.cms.service.dto.ViewPermissionCriteria;
import com.aidriveall.cms.service.dto.ViewPermissionDTO;
import com.aidriveall.cms.service.mapper.ViewPermissionMapper;

/**
 * Service for executing complex queries for {@link ViewPermission} entities in the database.
 * The main input is a {@link ViewPermissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ViewPermissionDTO} or a {@link Page} of {@link ViewPermissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ViewPermissionQueryService extends QueryService<ViewPermission> {

    private final Logger log = LoggerFactory.getLogger(ViewPermissionQueryService.class);

    private final ViewPermissionRepository viewPermissionRepository;

    private final EntityManager em;

    private final CommonTableRepository commonTableRepository;

    private final ViewPermissionMapper viewPermissionMapper;

    public ViewPermissionQueryService(ViewPermissionRepository viewPermissionRepository, EntityManager em, CommonTableRepository commonTableRepository, ViewPermissionMapper viewPermissionMapper) {
        this.viewPermissionRepository = viewPermissionRepository;
        this.em = em;
        this.commonTableRepository = commonTableRepository;
        this.viewPermissionMapper = viewPermissionMapper;
    }

    /**
     * Return a {@link List} of {@link ViewPermissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ViewPermissionDTO> findByCriteria(ViewPermissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ViewPermission> specification = createSpecification(criteria);
        return viewPermissionMapper.toDto(viewPermissionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ViewPermissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ViewPermissionDTO> findByCriteria(ViewPermissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ViewPermission> specification = createSpecification(criteria);
        return viewPermissionRepository.findAll(specification, page)
            .map(viewPermissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ViewPermissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ViewPermission> specification = createSpecification(criteria);
        return viewPermissionRepository.count(specification);
    }

    /**
     * Function to convert {@link ViewPermissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ViewPermission> createSpecification(ViewPermissionCriteria criteria) {
        Specification<ViewPermission> specification = Specification.where(null);
        if (criteria != null) {
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification = specification.or(buildSpecification(new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),ViewPermission_.id));
                    specification = specification.or(buildRangeSpecification(
                        (IntegerFilter)new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),ViewPermission_.order)
                    );
                } else {
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),ViewPermission_.text)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),ViewPermission_.i18n)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),ViewPermission_.link)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),ViewPermission_.externalLink)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),ViewPermission_.icon)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),ViewPermission_.code)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),ViewPermission_.description)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),ViewPermission_.apiPermissionCodes)
                    );
                }
            } else {
                if (criteria.getId() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getId(), ViewPermission_.id));
                }
                if (criteria.getText() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getText(), ViewPermission_.text));
                }
                if (criteria.geti18n() != null) {
                    specification = specification.and(buildStringSpecification(criteria.geti18n(), ViewPermission_.i18n));
                }
                if (criteria.getGroup() != null) {
                    specification = specification.and(buildSpecification(criteria.getGroup(), ViewPermission_.group));
                }
                if (criteria.getLink() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getLink(), ViewPermission_.link));
                }
                if (criteria.getExternalLink() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getExternalLink(), ViewPermission_.externalLink));
                }
                if (criteria.getTarget() != null) {
                    specification = specification.and(buildSpecification(criteria.getTarget(), ViewPermission_.target));
                }
                if (criteria.getIcon() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getIcon(), ViewPermission_.icon));
                }
                if (criteria.getDisabled() != null) {
                    specification = specification.and(buildSpecification(criteria.getDisabled(), ViewPermission_.disabled));
                }
                if (criteria.getHide() != null) {
                    specification = specification.and(buildSpecification(criteria.getHide(), ViewPermission_.hide));
                }
                if (criteria.getHideInBreadcrumb() != null) {
                    specification = specification.and(buildSpecification(criteria.getHideInBreadcrumb(), ViewPermission_.hideInBreadcrumb));
                }
                if (criteria.getShortcut() != null) {
                    specification = specification.and(buildSpecification(criteria.getShortcut(), ViewPermission_.shortcut));
                }
                if (criteria.getShortcutRoot() != null) {
                    specification = specification.and(buildSpecification(criteria.getShortcutRoot(), ViewPermission_.shortcutRoot));
                }
                if (criteria.getReuse() != null) {
                    specification = specification.and(buildSpecification(criteria.getReuse(), ViewPermission_.reuse));
                }
                if (criteria.getCode() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getCode(), ViewPermission_.code));
                }
                if (criteria.getDescription() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getDescription(), ViewPermission_.description));
                }
                if (criteria.getType() != null) {
                    specification = specification.and(buildSpecification(criteria.getType(), ViewPermission_.type));
                }
                if (criteria.getOrder() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getOrder(), ViewPermission_.order));
                }
                if (criteria.getApiPermissionCodes() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getApiPermissionCodes(), ViewPermission_.apiPermissionCodes));
                }
                if (criteria.getChildrenId() != null) {
                    specification = specification.and(buildSpecification(criteria.getChildrenId(),
                        root -> root.join(ViewPermission_.children, JoinType.LEFT).get(ViewPermission_.id)));
                }
                if (criteria.getApiPermissionsId() != null) {
                    specification = specification.and(buildSpecification(criteria.getApiPermissionsId(),
                        root -> root.join(ViewPermission_.apiPermissions, JoinType.LEFT).get(ApiPermission_.id)));
                }
                if (criteria.getParentId() != null) {
                    specification = specification.and(buildSpecification(criteria.getParentId(),
                        root -> root.join(ViewPermission_.parent, JoinType.LEFT).get(ViewPermission_.id)));
                }
                if (criteria.getAuthoritiesId() != null) {
                    specification = specification.and(buildSpecification(criteria.getAuthoritiesId(),
                        root -> root.join(ViewPermission_.authorities, JoinType.LEFT).get(Authority_.id)));
                }
            }
        }
        return specification;
    }

    @Transactional
    public boolean updateBySpecifield(String fieldName, Object value, ViewPermissionCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<ViewPermission> q = cb.createCriteriaUpdate(ViewPermission.class);
        CriteriaQuery<ViewPermission> sq = cb.createQuery(ViewPermission.class);
        Root<ViewPermission> root = q.from(ViewPermission.class);
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
     * @return Page<ViewPermissionDTO>
     */
    @Transactional(readOnly = true)
    public Page<ViewPermissionDTO> selectByCustomEntity(String entityName, ViewPermissionCriteria criteria, Predicate predicate, Pageable pageable) {
        List<ViewPermissionDTO> dataList = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createTupleQuery();
        Root<ViewPermission> root = q.from(ViewPermission.class);
        if (StringUtils.isEmpty(entityName)) {
            entityName = "ViewPermission";
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
                    root.join(commonTableRelationship.getRelationshipName(),JoinType.LEFT);
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
                ViewPermissionDTO viewPermissionDTO = BeanUtil.mapToBean(itemmap, ViewPermissionDTO.class, true);
                toManyRelationships.forEach(commonTableRelationship -> {
                    CriteriaQuery<Tuple> subQuery = cb.createTupleQuery();
                    Class<?> clazz = ((PluralAttributePath<?>)root.get(commonTableRelationship.getRelationshipName())).getAttribute().getElementType().getJavaType();
                    Root<?> manyRoot = subQuery.from(clazz);
                    if (clazz.getSimpleName().equals("ViewPermission")) {
                        BeanUtil.setFieldValue(
                            viewPermissionDTO,commonTableRelationship.getRelationshipName(),
                            this.selectByCustomEntity(
                                "ViewPermission",
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
                            BeanUtil.setFieldValue(viewPermissionDTO,commonTableRelationship.getRelationshipName(),subdataList);
                        } else {
                            // todo 暂时不予处理getOtherEntityField为null的情况。
                        }

                    }
                });
                dataList.add(viewPermissionDTO);
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
