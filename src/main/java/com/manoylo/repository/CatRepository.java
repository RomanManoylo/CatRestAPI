package com.manoylo.repository;

import com.manoylo.entity.Cat;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "cats", path = "cats")
public interface CatRepository extends PagingAndSortingRepository<Cat, Long> {

    List<Cat> findByName(@Param("name") String name);
    List<Cat> findById(@Param("id") long id);
}
