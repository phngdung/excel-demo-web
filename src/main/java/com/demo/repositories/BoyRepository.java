package com.demo.repositories;

import com.demo.entities.Boy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository
public interface BoyRepository extends CrudRepository<Boy, Long> {
    List<Boy> findByNameAndAgeAndCity(String name, String age, String city);

    @PersistenceContext EntityManager entityManager = null;;

    @Query(
            nativeQuery = true,
            value =
                    "select boy.* from boy where ?1 order by ?2 ")
    List<Map<String, Object>> findByOptions(String filter, String sortBy );

}