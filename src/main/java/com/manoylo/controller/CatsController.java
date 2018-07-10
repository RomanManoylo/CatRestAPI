package com.manoylo.controller;

import com.manoylo.entity.Cat;
import com.manoylo.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatsController {

    @Autowired
    private CatService catService;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="Cat") String name) {
        return name;
    }

    @RequestMapping("/create")
    public void createCat(@ModelAttribute Cat cat) {
        catService.createCat(cat);
    }

    @RequestMapping("/deleteAll")
    public void deleteAll() {
        catService.deleteAllCats();
    }

    @RequestMapping("/delete")
    public void delete(@RequestParam(value="id") long id) {
        catService.deleteCatById(id);
    }
}
