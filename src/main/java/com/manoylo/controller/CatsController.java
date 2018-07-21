package com.manoylo.controller;

import com.google.gson.Gson;
import com.manoylo.entity.Cat;
import com.manoylo.service.ImgService;
import com.manoylo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity<?> createCat(Principal principal, @RequestParam(value = "cat") String cat, @RequestParam(value = "image", required = false) MultipartFile file) {
        String imgName = imgService.saveImg(file);
        userService.addCat(principal.getName(),new Cat(new Gson().fromJson(cat,Cat.class) ,imgName));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping(value = "/createImg",method = RequestMethod.POST)
    public ResponseEntity<?> createCatImg(Principal principal, @RequestParam(value = "image", required = false) MultipartFile file) {
        String imgName = imgService.saveImg(file);
        Cat cat = new Cat("Tim","","2 age");
        userService.addCat(principal.getName(),new Cat(cat,imgName));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll(Principal principal) {
        userService.deleteAllCat(principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(Principal principal,@PathVariable(value = "id") long id) {
        if(userService.deleteCat(principal.getName(),id))
        return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> search(Principal principal, @PathVariable(value = "id") long id) {
        Optional<Cat> cat = userService.searchCat(id);
        return cat.<ResponseEntity<?>>map(cat1 -> new ResponseEntity<>(cat1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = imgService.loadResource(filename);
        if (file != null) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
