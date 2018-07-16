package com.manoylo.controller;

import com.manoylo.entity.Cat;
import com.manoylo.service.ImgService;
import com.manoylo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/cats")
public class CatsController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImgService imgService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllCat(Principal principal) {
        return new ResponseEntity<>(userService.getAllCat(principal.getName()),HttpStatus.OK);
    }

    @RequestMapping("/create")
    public ResponseEntity<?> createCat(Principal principal, @ModelAttribute Cat cat, @RequestParam(value = "image", required = false) MultipartFile file) {
        String imgName = imgService.saveImg(file);
        userService.addCat(principal.getName(),new Cat(cat,imgName));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping("/deleteAll")
    public ResponseEntity<?> deleteAll(Principal principal) {
        userService.deleteAllCat(principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/delete")
    public ResponseEntity<?> delete(Principal principal, @RequestParam(value = "id") long id) {
        if(userService.deleteCat(principal.getName(),id))
        return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @RequestMapping("/search")
    public ResponseEntity<?> search(Principal principal, @RequestParam(value = "id") long id) {
        Optional<Cat> cat = userService.searchCat(id);
        return cat.<ResponseEntity<?>>map(cat1 -> new ResponseEntity<>(cat1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
