package com.demo.repositories;


import com.demo.entities.Boy;
import com.demo.entities.BoyPage;
import com.demo.entities.BoySearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BoyCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public BoyCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Boy> findAllWithFilters(BoyPage boyPage,
                                        BoySearchCriteria BoySearchCriteria){
        CriteriaQuery<Boy> criteriaQuery = criteriaBuilder.createQuery(Boy.class);
        Root<Boy> boyRoot = criteriaQuery.from(Boy.class);
        Predicate predicate = getPredicate(BoySearchCriteria, boyRoot);
        criteriaQuery.where(predicate);
        setOrder(boyPage, criteriaQuery, boyRoot);

        TypedQuery<Boy> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(boyPage.getPageNumber() * boyPage.getPageSize());
        typedQuery.setMaxResults(boyPage.getPageSize());

        Pageable pageable = getPageable(boyPage);

        long BoysCount = getBoysCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, BoysCount);
    }

    private Predicate getPredicate(BoySearchCriteria boySearchCriteria,
                                   Root<Boy> boyRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(boySearchCriteria.getName())){
            predicates.add(
                    criteriaBuilder.like(boyRoot.get("name"),
                            "%" + boySearchCriteria.getName() + "%")
            );
        }
        if(Objects.nonNull(boySearchCriteria.getAge())){
            predicates.add(
                    criteriaBuilder.like(boyRoot.get("age"),
                            "%" + boySearchCriteria.getAge() + "%")
            );
        }
        if(Objects.nonNull(boySearchCriteria.getCity())){
            predicates.add(
                    criteriaBuilder.like(boyRoot.get("city"),
                            "%" + boySearchCriteria.getAge() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(BoyPage BoyPage,
                          CriteriaQuery<Boy> criteriaQuery,
                          Root<Boy> boyRoot) {
        if(BoyPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(boyRoot.get(BoyPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(boyRoot.get(BoyPage.getSortBy())));
        }
    }

    private Pageable getPageable(BoyPage boyPage) {
        Sort sort = Sort.by(boyPage.getSortDirection(), boyPage.getSortBy());
        return PageRequest.of(boyPage.getPageNumber(),boyPage.getPageSize(), sort);
    }

    private long getBoysCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Boy> countRoot = countQuery.from(Boy.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}