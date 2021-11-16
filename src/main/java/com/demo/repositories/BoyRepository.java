package com.demo.repositories;

import com.demo.entities.Boy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoyRepository extends CrudRepository<Boy, Long> {

}
