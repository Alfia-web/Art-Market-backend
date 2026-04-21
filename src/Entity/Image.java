package com.example.demo.entity;

import jakarta.persistence.*;
import com.example.demo.entity.Genres;
@jakarta.persistence.Entity
@Table(name="images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String nameImage;
    private String author;
    private String pathImage;
    private int width;
    private int height;
    private Genres genres;
    private Long userId;

    public Image(String nameImage, String author, String pathImage, int width, int height, Genres genres, Long userId){
        this.nameImage = nameImage;
        this.author = author;
        this.pathImage = pathImage;
        this.width = width;
        this.height = height;
        this.genres = Genres.Без_жанра;
        this.userId = userId;
    }

    public Image(){}

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getNameImage(){
        return nameImage;
    }
    public void setNameImage(String nameImage){
        this.nameImage=nameImage;
    }

    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author=author;
    }

    public String getPathImage(){
        return pathImage;
    }
    public void setPathImage(String pathImage){
        this.pathImage = pathImage;
    }

    public int getWidth(){
        return width;
    }
    public void setWidth(int width){
        this.width =width;
    }

    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
    }

    public Genres getGenres(){
        return genres;
    }

    public void setGenres(Genres genres){
        this.genres = genres;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
