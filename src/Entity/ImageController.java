package com.example.demo.controller;

import com.example.demo.entity.Image;
import com.example.demo.repository.ImageRepository;
import exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.sax.SAXResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageRepository repository;

    public ImageController(ImageRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Image> getAll() {
        return repository.findAll();
    }

    @PostMapping("/add")
    public Image addImage(@RequestParam("file") MultipartFile file, @RequestParam("nameImage") String name,
                             @RequestParam("author") String author, @RequestParam("width") int width,
                             @RequestParam("height") int height, @RequestParam("genre") com.example.demo.entity.Genres genres,
                             @RequestParam("userId") Long userId) throws IOException{
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get("images/" + filename);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        Image image = new Image();
        image.setNameImage(name);
        image.setAuthor(author);
        image.setWidth(width);
        image.setHeight(height);
        image.setGenres(genres);
        image.setPathImage("/images/" + filename);
        image.setUserId(userId);

        return repository.save(image);
    }

    @GetMapping("/user/{userId}")
    public List<Image> getByUser(@PathVariable Long userId){
        return repository.findByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Image updateImage(@PathVariable Long id, @RequestParam("file") MultipartFile file,
                             @RequestParam("nameImage") String name, @RequestParam("author") String author,
                             @RequestParam("width") int width, @RequestParam("height") int height,
                             @RequestParam("genre") com.example.demo.entity.Genres genres) throws IOException {

        Image image = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Картина не найдена"));
        image.setNameImage(name);
        image.setAuthor(author);
        image.setWidth(width);
        image.setHeight(height);
        image.setGenres(genres);

        if (file != null && !file.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("images/" + filename);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            image.setPathImage("/images/" + filename);
        }

        return repository.save(image);
    }
}