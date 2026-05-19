package controller;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Favorite;
import exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.FavoriteRepository;
import repository.ImageRepository;
import com.example.demo.entity.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    private final FavoriteRepository faveRepo;
    private final ImageRepository imgRepo;

    public FavoriteController(FavoriteRepository faveRepo, ImageRepository imgRepo) {
        this.faveRepo = faveRepo;
        this.imgRepo = imgRepo;
    }

    @Transactional
    @PostMapping("/toggle")
    public ResponseEntity<?> toggle(@RequestParam Long userId, @RequestParam Long imageId) {
        boolean isFav;
        if (faveRepo.existsByUserIdAndImageId(userId, imageId)) {
            faveRepo.deleteByUserIdAndImageId(userId, imageId);
            isFav = false;
        } else {
            Image image = imgRepo.findById(imageId)
                    .orElseThrow(() -> new ResourceNotFoundException("Картина не найдена"));
            Favorite fav = new Favorite();
            fav.setUserId(userId);
            fav.setImage(image);
            faveRepo.save(fav);
            isFav = true;
        }

        return ResponseEntity.ok(Map.of("isFavorite", isFav));
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestParam Long userId, @RequestParam Long imageId) {
        boolean exists = faveRepo.existsByUserIdAndImageId(userId, imageId);
        return ResponseEntity.ok(Map.of("isFavorite", exists));
    }

    @GetMapping("/user/{userId}")
    public List<Image> getFavorites(@PathVariable Long userId) {
        List<Favorite> favorites = faveRepo.findByUserId(userId);
        List<Image> images = new ArrayList<>();

        for (Favorite favorite : favorites) {
            images.add(favorite.getImage());
        }

        return images;
    }
}
