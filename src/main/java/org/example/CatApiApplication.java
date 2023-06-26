package org.example;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/cats")
public class CatApiApplication {

    private Map<String, CatPicture> catPictures = new HashMap<>();
    private int nextId = 1;

    @PostMapping
    public ResponseEntity<String> uploadCatPic(@RequestParam String name, @RequestParam("file") MultipartFile file) {
        try {
            String id = String.valueOf(nextId++);
            byte[] imageData = file.getBytes();
            CatPicture catPicture = new CatPicture(id, name, imageData);
            catPictures.put(id, catPicture);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cat picture uploaded successfully. ID: " + id);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload cat picture.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCatPic(@PathVariable String id) {
        if (catPictures.containsKey(id)) {
            catPictures.remove(id);
            return ResponseEntity.ok("Cat picture deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCatPic(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try {
            if (catPictures.containsKey(id)) {
                byte[] imageData = file.getBytes();
                CatPicture updatedCatPicture = new CatPicture(id, file.getOriginalFilename(), imageData);
                catPictures.put(id, updatedCatPicture);
                return ResponseEntity.ok("Cat picture updated successfully.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update cat picture.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getCatPicById(@PathVariable String id) {
        if (catPictures.containsKey(id)) {
            CatPicture catPicture = catPictures.get(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(catPicture.getImageData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CatPicture>> getAllCatPics() {
        List<CatPicture> catPictureList = new ArrayList<>(catPictures.values());
        return ResponseEntity.ok(catPictureList);
    }

    public static void main(String[] args) {
        SpringApplication.run(CatApiApplication.class, args);
    }
}

