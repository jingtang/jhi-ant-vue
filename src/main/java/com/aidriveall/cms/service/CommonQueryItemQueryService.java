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

import com.aidriveall.cms.domain.CommonQueryItem;
import com.aidriveall.cms.domain.*; // for static metamodels
import com.aidriveall.cms.repository.CommonTableRepository;
import com.aidriveall.cms.domain.enumeration.RelationshipType;
import com.aidriveall.cms.repository.CommonQueryItemRepository;
import com.aidriveall.cms.service.dto.CommonQueryItemCriteria;
import com.aidriveall.cms.service.dto.CommonQueryItemDTO;
import com.aidriveall.cms.service.mapper.CommonQueryItemMapper;

/**
 * Service for executing complex queries for {@link CommonQueryItem} entities in the database.
 * The main input is a {@link CommonQueryItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommonQueryItemDTO} or a {@link Page} of {@link CommonQueryItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommonQueryItemQueryService extends QueryService<CommonQueryItem> {

    private final Logger log = LoggerFactory.getLogger(CommonQueryItemQueryService.class);

    private final CommonQueryItemRepository commonQueryItemRepository;

    private final EntityManager em;

    private final CommonTableRepository commonTableRepository;

    private final CommonQueryItemMapper commonQueryItemMapper;

    public CommonQueryItemQueryService(CommonQueryItemRepository commonQueryItemRepository, EntityManager em, CommonTableRepository commonTableRepository, CommonQueryItemMapper commonQueryItemMapper) {
        this.commonQueryItemRepository = commonQueryItemRepository;
        this.em = em;
        this.commonTableRepository = commonTableRepository;
        this.commonQueryItemMapper = commonQueryItemMapper;
    }

    /**
     * Return a {@link List} of {@link CommonQueryItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommonQueryItemDTO> findByCriteria(CommonQueryItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommonQueryItem> specification = createSpecification(criteria);
        return commonQueryItemMapper.toDto(commonQueryItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommonQueryItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonQueryItemDTO> findByCriteria(CommonQueryItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommonQueryItem> specification = createSpecification(criteria);
        return commonQueryItemRepository.findAll(specification, page)
            .map(commonQueryItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommonQueryItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommonQueryItem> specification = createSpecification(criteria);
        return commonQueryItemRepository.count(specification);
    }

    /**
     * Function to convert {@link CommonQueryItemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CommonQueryItem> createSpecification(CommonQueryItemCriteria criteria) {
        Specification<CommonQueryItem> specification = Specification.where(null);
        if (criteria != null) {
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification = specification.or(buildSpecification(new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),CommonQueryItem_.id));
                    specification = specification.or(buildRangeSpecification(
                        (IntegerFilter)new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),CommonQueryItem_.order)
                    );
                } else {
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonQueryItem_.prefix)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonQueryItem_.fieldName)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonQueryItem_.fieldType)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonQueryItem_.operator)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonQueryItem_.value)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonQueryItem_.suffix)
                    );
                }
            } else {
                if (criteria.getId() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getId(), CommonQueryItem_.id));
                }
                if (criteria.getPrefix() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getPrefix(), CommonQueryItem_.prefix));
                }
                if (criteria.getFieldName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getFieldName(), CommonQueryItem_.fieldName));
                }
                if (criteria.getFieldType() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getFieldType(), CommonQueryItem_.fieldType));
                }
                if (criteria.getOperator() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getOperator(), CommonQueryItem_.operator));
                }
                if (criteria.getValue() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getValue(), CommonQueryItem_.value));
                }
                if (criteria.getSuffix() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getSuffix(), CommonQueryItem_.suffix));
                }
                if (criteria.getOrder() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getOrder(), CommonQueryItem_.order));
                }
                if (criteria.getQueryId() != null) {
                    specification = specification.and(buildSpecification(criteria.getQueryId(),
                        root -> root.join(CommonQueryItem_.query, JoinType.LEFT).get(CommonQuery_.id)));
                }
            }
        }
        return specification;
    }

    @Transactional
    public boolean updateBySpecifield(String fieldName, Object value, CommonQueryItemCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<CommonQueryItem> q = cb.createCriteriaUpdate(CommonQueryItem.class);
        CriteriaQuery<CommonQueryItem> sq = cb.createQuery(CommonQueryItem.class);
        Root<CommonQueryItem> root = q.from(CommonQueryItem.class);
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
     * @return Page<CommonQueryItemDTO>
     */
    @Transactional(readOnly = true)
    public Page<CommonQueryItemDTO> selectByCustomEntity(String entityName, CommonQueryItemCriteria criteria, Predicate predicate, Pageable pageable) {
        List<CommonQueryItemDTO> dataList = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createTupleQuery();
        Root<CommonQueryItem> root = q.from(CommonQueryItem.class);
        if (StringUtils.isEmpty(entityName)) {
            entityName = "CommonQueryItem";
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
                CommonQueryItemDTO commonQueryItemDTO = BeanUtil.mapToBean(itemmap, CommonQueryItemDTO.class, true);
                toManyRelationships.forEach(commonTableRelationship -> {
                    CriteriaQuery<Tuple> subQuery = cb.createTupleQuery();
                    Class<?> clazz = ((PluralAttributePath<?>)root.get(commonTableRelationship.getRelationshipName())).getAttribute().getElementType().getJavaType();
                    Root<?> manyRoot = subQuery.from(clazz);
                    if (clazz.getSimpleName().equals("CommonQueryItem")) {
                        BeanUtil.setFieldValue(
                            commonQueryItemDTO,commonTableRelationship.getRelationshipName(),
                            this.selectByCustomEntity(
                                "CommonQueryItem",
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
                            BeanUtil.setFieldValue(commonQueryItemDTO,commonTableRelationship.getRelationshipName(),subdataList);
                        } else {
                            // todo 暂时不予处理getOtherEntityField为null的情况。
                        }

                    }
                });
                dataList.add(commonQueryItemDTO);
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
