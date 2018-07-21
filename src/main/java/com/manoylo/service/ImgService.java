package com.manoylo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service("imgService")
@Transactional
public class ImgService {

    private final Path rootLocation;
    private final String location="CatPhotos";

    @Autowired
    public ImgService() {
        this.rootLocation = Paths.get(location);
        deleteAll();
        init();
    }

    private void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
        }
    }

    public String saveImg(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                String ext = getFileExtension(file);
                UUID uid = UUID.randomUUID();
                String nameImg = uid.toString() + "." + ext;
                if (save(file, nameImg)) {
                    return nameImg;
                } else
                    return "";
            } catch (Exception e) {
            }
        }
        return "";
    }

    private boolean save(MultipartFile file, String name) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(name));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public String getFileExtension(MultipartFile  file) {
        String fileName = file.getOriginalFilename();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }


    public Resource loadResource(String filename) {
        try {
            Path file =  loadPath(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }

        } catch (MalformedURLException e) {
           return null;
        }
        return null;
    }

    public Path loadPath(String filename) {
        return rootLocation.resolve(filename);
    }


}
