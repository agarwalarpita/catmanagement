package org.example;

import lombok.Data;

@Data
public class CatPicture {
    private String id;
    private String name;
    private byte[] imageData;

    public CatPicture(String id, String name, byte[] imageData) {
        this.id = id;
        this.name = name;
        this.imageData = imageData;
    }
}
