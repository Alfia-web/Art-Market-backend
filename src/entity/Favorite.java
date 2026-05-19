package com.example.demo.entity;

import jakarta.persistence.*;
import com.example.demo.entity.Image;

@Entity
@Table(name = "favorite", uniqueConstraints= @UniqueConstraint(columnNames={"userId", "imageId"}))
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name="imageId")
    private  Image image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
