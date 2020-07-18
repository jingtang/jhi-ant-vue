package com.aidriveall.cms.service;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import antlr.ParseTree;
import cn.hutool.core.bean.BeanUtil;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;

import cn.hutool.core.bean.DynaBean;
import com.aidriveall.cms.anltr.CriteriaLogicExprListener;
import com.aidriveall.cms.anltr.LogicExprLexer;
import com.aidriveall.cms.anltr.LogicExprParser;
import io.github.jhipster.service.filter.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.lang3.ClassUtils;
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

import com.aidriveall.cms.domain.CommonQuery;
import com.aidriveall.cms.domain.*; // for static metamodels
import com.aidriveall.cms.repository.CommonTableRepository;
import com.aidriveall.cms.domain.enumeration.RelationshipType;
import com.aidriveall.cms.repository.CommonQueryRepository;
import com.aidriveall.cms.service.dto.CommonQueryCriteria;
import com.aidriveall.cms.service.dto.CommonQueryDTO;
import com.aidriveall.cms.service.mapper.CommonQueryMapper;

/**
 * Service for executing complex queries for {@link CommonQuery} entities in the database.
 * The main input is a {@link CommonQueryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommonQueryDTO} or a {@link Page} of {@link CommonQueryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommonQueryQueryService extends QueryService<CommonQuery> {

    private final Logger log = LoggerFactory.getLogger(CommonQueryQueryService.class);

    public static Map<String, Object> specificationMap = new HashMap<>();

    private final CommonQueryRepository commonQueryRepository;

    private final EntityManager em;

    private final CommonTableRepository commonTableRepository;

    private final CommonQueryMapper commonQueryMapper;

    public CommonQueryQueryService(CommonQueryRepository commonQueryRepository, EntityManager em, CommonTableRepository commonTableRepository, CommonQueryMapper commonQueryMapper) {
        this.commonQueryRepository = commonQueryRepository;
        this.em = em;
        this.commonTableRepository = commonTableRepository;
        this.commonQueryMapper = commonQueryMapper;
    }

    /**
     * Return a {@link List} of {@link CommonQueryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommonQueryDTO> findByCriteria(CommonQueryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommonQuery> specification = createSpecification(criteria);
        return commonQueryMapper.toDto(commonQueryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommonQueryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommonQueryDTO> findByCriteria(CommonQueryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommonQuery> specification = createSpecification(criteria);
        return commonQueryRepository.findAll(specification, page)
            .map(commonQueryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommonQueryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommonQuery> specification = createSpecification(criteria);
        return commonQueryRepository.count(specification);
    }

    /**
     * Function to convert {@link CommonQueryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CommonQuery> createSpecification(CommonQueryCriteria criteria) {
        Specification<CommonQuery> specification = Specification.where(null);
        if (criteria != null) {
            if (StringUtils.isNotEmpty(criteria.getJhiCommonSearchKeywords())) {
                if (StringUtils.isNumeric(criteria.getJhiCommonSearchKeywords())) {
                    specification = specification.or(buildSpecification(new LongFilter().setEquals(Long.valueOf(criteria.getJhiCommonSearchKeywords())),CommonQuery_.id));
                } else {
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonQuery_.name)
                    );
                    specification = specification.or(buildStringSpecification(
                        new StringFilter().setContains(criteria.getJhiCommonSearchKeywords()),CommonQuery_.description)
                    );
                }
            } else {
                if (criteria.getId() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getId(), CommonQuery_.id));
                }
                if (criteria.getName() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getName(), CommonQuery_.name));
                }
                if (criteria.getDescription() != null) {
                    specification = specification.and(buildStringSpecification(criteria.getDescription(), CommonQuery_.description));
                }
                if (criteria.getLastModifiedTime() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getLastModifiedTime(), CommonQuery_.lastModifiedTime));
                }
                if (criteria.getItemsId() != null) {
                    specification = specification.and(buildSpecification(criteria.getItemsId(),
                        root -> root.join(CommonQuery_.items, JoinType.LEFT).get(CommonQueryItem_.id)));
                }
                if (criteria.getModifierId() != null) {
                    specification = specification.and(buildSpecification(criteria.getModifierId(),
                        root -> root.join(CommonQuery_.modifier, JoinType.LEFT).get(User_.id)));
                }
                if (criteria.getCommonTableId() != null) {
                    specification = specification.and(buildSpecification(criteria.getCommonTableId(),
                        root -> root.join(CommonQuery_.commonTable, JoinType.LEFT).get(CommonTable_.id)));
                }
                if (criteria.getCommonTableClazzName() != null) {
                    specification = specification.and(buildSpecification(criteria.getCommonTableClazzName(),
                        root -> root.join(CommonQuery_.commonTable, JoinType.LEFT).get(CommonTable_.clazzName)));
                }
            }
        }
        return specification;
    }

    @Transactional
    public boolean updateBySpecifield(String fieldName, Object value, CommonQueryCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<CommonQuery> q = cb.createCriteriaUpdate(CommonQuery.class);
        CriteriaQuery<CommonQuery> sq = cb.createQuery(CommonQuery.class);
        Root<CommonQuery> root = q.from(CommonQuery.class);
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
     * @return Page<CommonQueryDTO>
     */
    @Transactional(readOnly = true)
    public Page<CommonQueryDTO> selectByCustomEntity(String entityName, CommonQueryCriteria criteria, Predicate predicate, Pageable pageable) {
        List<CommonQueryDTO> dataList = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> q = cb.createTupleQuery();
        Root<CommonQuery> root = q.from(CommonQuery.class);
        if (StringUtils.isEmpty(entityName)) {
            entityName = "CommonQuery";
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
                CommonQueryDTO commonQueryDTO = BeanUtil.mapToBean(itemmap, CommonQueryDTO.class, true);
                toManyRelationships.forEach(commonTableRelationship -> {
                    CriteriaQuery<Tuple> subQuery = cb.createTupleQuery();
                    Class<?> clazz = ((PluralAttributePath<?>)root.get(commonTableRelationship.getRelationshipName())).getAttribute().getElementType().getJavaType();
                    Root<?> manyRoot = subQuery.from(clazz);
                    if (clazz.getSimpleName().equals("CommonQuery")) {
                        BeanUtil.setFieldValue(
                            commonQueryDTO,commonTableRelationship.getRelationshipName(),
                            this.selectByCustomEntity(
                                "CommonQuery",
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
                            BeanUtil.setFieldValue(commonQueryDTO,commonTableRelationship.getRelationshipName(),subdataList);
                        } else {
                            // todo 暂时不予处理getOtherEntityField为null的情况。
                        }

                    }
                });
                dataList.add(commonQueryDTO);
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

    // 把commonQuery转为Specification
    public Specification createSpecification(Long commonQueryId) throws ClassNotFoundException {
        CommonQuery commonQuery = commonQueryRepository.getOne(commonQueryId);
        String packageName = ClassUtils.getPackageName(CommonQuery.class);
        String servicePackageName = ClassUtils.getPackageName(this.getClass());
        Specification specification;
        if (commonQuery != null) {
            Class targetClass = Class.forName(packageName + "." + commonQuery.getCommonTable().getClazzName() + "_");
            Class targetCriteriaClass = Class.forName(servicePackageName + ".dto." + commonQuery.getCommonTable().getClazzName() + "Criteria");
            StringBuffer s = new StringBuffer();
            Set<CommonQueryItem> queryItems = commonQuery.getItems();
            queryItems.forEach( queryItem -> {
                String prefix = queryItem.getPrefix();
                String suffix = queryItem.getSuffix();
                if (StringUtils.isNotEmpty(prefix)) {
                    switch (prefix) {
                        case ")":
                            s.append(" )");
                            break;
                        case "(":
                            s.append(" (");
                            break;
                        case "AND":
                            s.append(" AND");
                            break;
                        case "OR":
                            s.append(" AND");
                            break;
                        default:

                    }
                }
                try {
                    Integer specId = specificationMap.size()+1;
                    specificationMap.put(queryItem.getId() + "_" + specId, createSpecificationByQueryItem(queryItem,targetClass,targetCriteriaClass));
                    s.append(" " +queryItem.getId() + "_" + specId);
                } catch (NoSuchFieldException | InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (StringUtils.isNotEmpty(suffix)) {
                    switch (suffix) {
                        case ")":
                            s.append(" )");
                            break;
                        case "(":
                            s.append(" (");
                            break;
                        case "AND":
                            s.append(" AND");
                            break;
                        case "OR":
                            s.append(" AND");
                            break;
                        default:
                    }
                }
            });
            ANTLRInputStream input = new ANTLRInputStream(s.toString());
            LogicExprLexer lexer = new LogicExprLexer(input);
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            LogicExprParser parser = new LogicExprParser(tokenStream);
            LogicExprParser.StatContext parseTree = parser.stat();
            CriteriaLogicExprListener visitor = new CriteriaLogicExprListener();
            System.out.println(parseTree.toStringTree(parser)); //打印规则数
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(visitor, parseTree);
            specification = Specification.where(visitor.specifications.get(parseTree));
            return specification;
        } else {
            return null;
        }

    }

    private Specification createSpecificationByQueryItem(CommonQueryItem commonQueryItem,Class jPAMetaModelClass, Class targetCriteriaClass) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        if (StringUtils.isNotEmpty(commonQueryItem.getFieldName()) &&
            StringUtils.isNotEmpty(commonQueryItem.getOperator()) &&
            StringUtils.isNotEmpty(commonQueryItem.getValue())
        ) {
            DynaBean criteria = DynaBean.create(targetCriteriaClass.newInstance());
            DynaBean filter;
            switch (commonQueryItem.getFieldType()) {
                case "LONG":
                    filter = DynaBean.create(LongFilter.class);
                    filter.set(commonQueryItem.getOperator(), Long.parseLong(commonQueryItem.getValue()));
                    criteria.set(commonQueryItem.getFieldName(), filter.getBean());
                    return buildRangeSpecification(criteria.get(commonQueryItem.getFieldName()),
                        (SingularAttribute)jPAMetaModelClass.getField(commonQueryItem.getFieldName()).get(targetCriteriaClass));
                case "INTEGER":
                    filter = DynaBean.create(IntegerFilter.class);
                    filter.set(commonQueryItem.getOperator(), Integer.parseInt(commonQueryItem.getValue()));
                    criteria.set(commonQueryItem.getFieldName(), filter.getBean());
                    return buildRangeSpecification(criteria.get(commonQueryItem.getFieldName()),
                        (SingularAttribute)jPAMetaModelClass.getField(commonQueryItem.getFieldName()).get(targetCriteriaClass));
                case "STRING":
                    filter = DynaBean.create(new StringFilter());
                    filter.set(commonQueryItem.getOperator(),commonQueryItem.getValue());
                    criteria.set(commonQueryItem.getFieldName(), filter.getBean());
                    return buildStringSpecification(criteria.get(commonQueryItem.getFieldName()),
                        (SingularAttribute)jPAMetaModelClass.getField(commonQueryItem.getFieldName()).get(targetCriteriaClass));
                case "FLOAT":
                    filter = DynaBean.create(FloatFilter.class);
                    filter.set(commonQueryItem.getOperator(), Float.parseFloat(commonQueryItem.getValue()));
                    criteria.set(commonQueryItem.getFieldName(), filter.getBean());
                    return buildRangeSpecification(criteria.get(commonQueryItem.getFieldName()),
                        (SingularAttribute)jPAMetaModelClass.getField(commonQueryItem.getFieldName()).get(targetCriteriaClass));
                case "DOUBLE":
                    filter = DynaBean.create(DoubleFilter.class);
                    filter.set(commonQueryItem.getOperator(), Double.parseDouble(commonQueryItem.getValue()));
                    criteria.set(commonQueryItem.getFieldName(), filter.getBean());
                    return buildRangeSpecification(criteria.get(commonQueryItem.getFieldName()),
                        (SingularAttribute)jPAMetaModelClass.getField(commonQueryItem.getFieldName()).get(targetCriteriaClass));
                case "BOOLEAN":
                    filter = DynaBean.create(BooleanFilter.class);
                    filter.set(commonQueryItem.getOperator(), Boolean.parseBoolean(commonQueryItem.getValue()));
                    criteria.set(commonQueryItem.getFieldName(), filter.getBean());
                    return buildSpecification(criteria.get(commonQueryItem.getFieldName()),
                        (SingularAttribute)jPAMetaModelClass.getField(commonQueryItem.getFieldName()).get(targetCriteriaClass));
                case "ZONED_DATE_TIME":
                    filter = DynaBean.create(ZonedDateTimeFilter.class);
                    filter.set(commonQueryItem.getOperator(), ZonedDateTime.parse(commonQueryItem.getValue()));
                    criteria.set(commonQueryItem.getFieldName(), filter.getBean());
                    return buildRangeSpecification(criteria.get(commonQueryItem.getFieldName()),
                        (SingularAttribute)jPAMetaModelClass.getField(commonQueryItem.getFieldName()).get(targetCriteriaClass));
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

}
