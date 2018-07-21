package com.manoylo.repository;

import com.manoylo.entity.Cat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CatRepository extends CrudRepository<Cat, Long> {

    List<Cat> findByName(@Param("name") String name);
}
