package com.manoylo.service;

import com.manoylo.entity.Cat;
import com.manoylo.repository.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("catService")
public class CatService {
    @Autowired
    private CatRepository catRepository;

    public void setCatRepository(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public void createCat(Cat cat) {
        cat
        catRepository.save(cat);
    }


    public void deleteAllCats() {
        catRepository.deleteAll();
    }

    public void deleteCatById(long id) {
        catRepository.deleteById(id);
    }
}
