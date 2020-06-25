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

import com.aidriveall.cms.domain.CommonTableRelationship;
import com.aidriveall.cms.domain.*; // for static metamodels
import com.aidriveall.cms.repository.CommonTableRepository;
import com.aidriveall.cms.domain.enumeration.RelationshipType;
import com.aidriveall.cms.repository.CommonTableRelationshipRepository;
import com.aidriveall.cms.service.dto.CommonTableRelationshipCriteria;
import com.aidriveall.cms.service.dto.CommonTableRelationshipDTO;
import com.aidriveall.cms.service.mapper.CommonTableRelationshipMapper;

/**
 * Service for executing complex queries for {@link CommonTableRelationship} entities in the database.
 * The main input is a {@link CommonTableRelationshipCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommonTableRelationshipDTO} or a {@link Page} of {@link CommonTableRelationshipDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommonTableRelationshipQueryService extends QueryService<CommonTableRelationship> {

    private final Logger log = LoggerFactory.getLogger(CommonTableRelationshipQueryService.class);

    private final CommonTableRelationshipRepository commonTableRelationshipRepository;

    private final EntityManager em;

    private final CommonTableRepository commonTableRepository;

    private final CommonTableRelationshipMapper commonTableRelationshipMapper;

    public CommonTableRelationshipQueryService(CommonTableRelationshipRepository commonTableRelationshipRepository, EntityManager em, CommonTableRepository commonTableRepository, CommonTableRelationshipMapper commonTableRelationshipMapper) {
        this.commonTableRelationshipRepository = commonTableRelationshipRepository;
        this.em = em;
        this.commonTableRepository = commonTableRepository;
        this.commonTableRelationshipMapper = commonTableRelationshipMapper;
    }

    /**
     * Return a {@link List} of {@link CommonTableRelationshipDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommonTableRelationshipDTO> findByCriteria(CommonTableRelationshipCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommonTableRelationship> specification = createSpecification(criteria);
        return commonTableRelationshipMapper.toDto(commonTableRelationshipRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommonTableRelationshipDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonTableRelationshipDTO> findByCriteria(CommonTableRelationshipCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommonTableRelationship> specification = createSpecification(criteria);
        return commonTableRelationshipRepository.findAll(specification, page)
            .map(commonTableRelationshipMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommonTableRelationshipCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommonTableRelationship> specification = createSpecification(criteria);
        return commonTableRelationshipRepository.count(specification);
    }

    /**
     * Function to convert {@link CommonTableRelationshipCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CommonTableRelationship> createSpecification(CommonTableRelationshipCriteria criteria) {
        Specification<CommonTableRelationship> specification = Specification.where(null);
        if (criteria != null) {
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification = specification.or(buildSpecification(new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),CommonTableRelationship_.id));
                    specification = specification.or(buildRangeSpecification(
                        (IntegerFilter)new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),CommonTableRelationship_.columnWidth)
                    );
                    specification = specification.or(buildRangeSpecification(
                        (IntegerFilter)new IntegerFilter().setEquals(Integer.valueOf(criteria.getJhiCommonSearchKeywords())),CommonTableRelationship_.order)
                    );
                } else {
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonTableRelationship_.name)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonTableRelationship_.otherEntityField)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonTableRelationship_.otherEntityName)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonTableRelationship_.relationshipName)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonTableRelationship_.otherEntityRelationshipName)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonTableRelationship_.fontColor)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonTableRelationship_.backgroundColor)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonTableRelationship_.help)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonTableRelationship_.dataName)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonTableRelationship_.webComponentType)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonTableRelationship_.dataDictionaryCode)
                    );
                }
            } else {
                if (criteria.getId() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getId(), CommonTableRelationship_.id));
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), CommonTableRelationship_.name));
                }
                if (criteria.getRelationshipType() != null) {
                    specification = specification.and(buildSpecification(criteria.getRelationshipType(), CommonTableRelationship_.relationshipType));
                }
                if (criteria.getSourceType() != null) {
                    specification = specification.and(buildSpecification(criteria.getSourceType(), CommonTableRelationship_.sourceType));
                }
                if (criteria.getOtherEntityField() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getOtherEntityField(), CommonTableRelationship_.otherEntityField));
                }
                if (criteria.getOtherEntityName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getOtherEntityName(), CommonTableRelationship_.otherEntityName));
                }
                if (criteria.getRelationshipName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getRelationshipName(), CommonTableRelationship_.relationshipName));
                }
                if (criteria.getOtherEntityRelationshipName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getOtherEntityRelationshipName(), CommonTableRelationship_.otherEntityRelationshipName));
                }
                if (criteria.getColumnWidth() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getColumnWidth(), CommonTableRelationship_.columnWidth));
                }
                if (criteria.getOrder() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getOrder(), CommonTableRelationship_.order));
                }
                if (criteria.getFixed() != null) {
                    specification = specification.and(buildSpecification(criteria.getFixed(), CommonTableRelationship_.fixed));
                }
                if (criteria.getEditInList() != null) {
                    specification = specification.and(buildSpecification(criteria.getEditInList(), CommonTableRelationship_.editInList));
                }
                if (criteria.getEnableFilter() != null) {
                    specification = specification.and(buildSpecification(criteria.getEnableFilter(), CommonTableRelationship_.enableFilter));
                }
                if (criteria.getHideInList() != null) {
                    specification = specification.and(buildSpecification(criteria.getHideInList(), CommonTableRelationship_.hideInList));
                }
                if (criteria.getHideInForm() != null) {
                    specification = specification.and(buildSpecification(criteria.getHideInForm(), CommonTableRelationship_.hideInForm));
                }
                if (criteria.getFontColor() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getFontColor(), CommonTableRelationship_.fontColor));
                }
                if (criteria.getBackgroundColor() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getBackgroundColor(), CommonTableRelationship_.backgroundColor));
                }
                if (criteria.getHelp() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getHelp(), CommonTableRelationship_.help));
                }
                if (criteria.getOwnerSide() != null) {
                    specification = specification.and(buildSpecification(criteria.getOwnerSide(), CommonTableRelationship_.ownerSide));
                }
                if (criteria.getDataName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getDataName(), CommonTableRelationship_.dataName));
                }
                if (criteria.getWebComponentType() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getWebComponentType(), CommonTableRelationship_.webComponentType));
                }
                if (criteria.getOtherEntityIsTree() != null) {
                    specification = specification.and(buildSpecification(criteria.getOtherEntityIsTree(), CommonTableRelationship_.otherEntityIsTree));
                }
                if (criteria.getShowInFilterTree() != null) {
                    specification = specification.and(buildSpecification(criteria.getShowInFilterTree(), CommonTableRelationship_.showInFilterTree));
                }
                if (criteria.getDataDictionaryCode() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getDataDictionaryCode(), CommonTableRelationship_.dataDictionaryCode));
                }
                if (criteria.getClientReadOnly() != null) {
                    specification = specification.and(buildSpecification(criteria.getClientReadOnly(), CommonTableRelationship_.clientReadOnly));
                }
                if (criteria.getRelationEntityId() != null) {
                    specification = specification.and(buildSpecification(criteria.getRelationEntityId(),
                        root -> root.join(CommonTableRelationship_.relationEntity, JoinType.LEFT).get(CommonTable_.id)));
                }
                if (criteria.getDataDictionaryNodeId() != null) {
                    specification = specification.and(buildSpecification(criteria.getDataDictionaryNodeId(),
                        root -> root.join(CommonTableRelationship_.dataDictionaryNode, JoinType.LEFT).get(DataDictionary_.id)));
                }
                if (criteria.getCommonTableId() != null) {
                    specification = specification.and(buildSpecification(criteria.getCommonTableId(),
                        root -> root.join(CommonTableRelationship_.commonTable, JoinType.LEFT).get(CommonTable_.id)));
                }
            }
        }
        return specification;
    }

    @Transactional
    public boolean updateBySpecifield(String fieldName, Object value, CommonTableRelationshipCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<CommonTableRelationship> q = cb.createCriteriaUpdate(CommonTableRelationship.class);
        CriteriaQuery<CommonTableRelationship> sq = cb.createQuery(CommonTableRelationship.class);
        Root<CommonTableRelationship> root = q.from(CommonTableRelationship.class);
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
     * @return Page<CommonTableRelationshipDTO>
     */
    @Transactional(readOnly = true)
    public Page<CommonTableRelationshipDTO> selectByCustomEntity(String entityName, CommonTableRelationshipCriteria criteria, Predicate predicate, Pageable pageable) {
        List<CommonTableRelationshipDTO> dataList = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<CommonTableRelationship> countRoot = countQuery.from(CommonTableRelationship.class);
        CriteriaQuery<Tuple> q = cb.createTupleQuery();
        Root<CommonTableRelationship> root = q.from(CommonTableRelationship.class);
        if (StringUtils.isEmpty(entityName)) {
            entityName = "CommonTableRelationship";
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
//        countQuery.select(cb.countDistinct(countRoot.get("id")));
        q.multiselect(s);
        Predicate criteriaPredicate = createSpecification(criteria).toPredicate(root, q, cb);
        if (criteriaPredicate != null) {
            q.where(criteriaPredicate);
//            countQuery.where(criteriaPredicate);
        } else if (predicate != null) {
            q.where(predicate);
//            countQuery.where(predicate);
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
                CommonTableRelationshipDTO commonTableRelationshipDTO = BeanUtil.mapToBean(itemmap, CommonTableRelationshipDTO.class, true);
                toManyRelationships.forEach(commonTableRelationship -> {
                    CriteriaQuery<Tuple> subQuery = cb.createTupleQuery();
                    Class<?> clazz = ((PluralAttributePath<?>)root.get(commonTableRelationship.getRelationshipName())).getAttribute().getElementType().getJavaType();
                    Root<?> manyRoot = subQuery.from(clazz);
                    if (clazz.getSimpleName().equals("CommonTableRelationship")) {
                        BeanUtil.setFieldValue(
                            commonTableRelationshipDTO,commonTableRelationship.getRelationshipName(),
                            this.selectByCustomEntity(
                                "CommonTableRelationship",
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
                            BeanUtil.setFieldValue(commonTableRelationshipDTO,commonTableRelationship.getRelationshipName(),subdataList);
                        } else {
                            // todo 暂时不予处理getOtherEntityField为null的情况。
                        }

                    }
                });
                dataList.add(commonTableRelationshipDTO);
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
