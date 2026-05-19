package controller;

import com.example.demo.entity.Image;
import repository.AuctionRepository;
import repository.ImageRepository;
import exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.example.demo.entity.Genres;
import repository.UserRepository;
import com.example.demo.entity.Auction;
import com.example.demo.entity.AuctionStatus;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageRepository imgRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    public ImageController(ImageRepository imgRepository, UserRepository userRepository, AuctionRepository auctionRepository) {
        this.imgRepository= imgRepository;
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
    }

    @GetMapping
    public List<Image> getAll() {
        return imgRepository.findAll();
    }

    @PostMapping("/add")
    public Image addImage(@RequestParam("file") MultipartFile file, @RequestParam("nameImage") String name,
                          @RequestParam("author") String author, @RequestParam("width") int width,
                          @RequestParam("height") int height, @RequestParam("genre") String genreStr,
                          @RequestParam("userId") Long userId,
                          @RequestParam("startPrice") BigDecimal startPrice,
                          @RequestParam("startTime") String startTime,
                          @RequestParam("endTime") String endTime) throws IOException{

            System.out.println(startPrice);
            System.out.println(startTime);
            System.out.println(endTime);
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("images/" + filename);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            System.out.println("genre=" + genreStr + " userId=" + userId);
            Genres genres;
            try {
                genres = Genres.valueOf(genreStr);
            } catch (IllegalArgumentException e) {
                genres = Genres.Без_жанра;
            }

            Image image = new Image();
            image.setNameImage(name);
            image.setAuthor(author);
            image.setWidth(width);
            image.setHeight(height);
            image.setGenres(genres);
            image.setPathImage("/images/" + filename);
            com.example.demo.entity.User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
            image.setUser(user);

            Image saved = imgRepository.save(image);

            Auction auction = new Auction();
            auction.setImage(saved);
            auction.setStartPrice(startPrice);
            auction.setCurrentPrice(startPrice);
            auction.setStartTime(LocalDateTime.parse(startTime));
            auction.setEndTime(LocalDateTime.parse(endTime));
            auction.setStatus(AuctionStatus.PENDING);
            auctionRepository.save(auction);

            return saved;
    }

    @GetMapping("/user/{userId}")
    public List<Image> getByUser(@PathVariable Long userId){
        return imgRepository.findByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id){
        imgRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Image updateImage(@PathVariable Long id, @RequestParam(value = "file", required = false) MultipartFile file,
                             @RequestParam("nameImage") String name, @RequestParam("author") String author,
                             @RequestParam("width") int width, @RequestParam("height") int height,
                             @RequestParam("genre") com.example.demo.entity.Genres genres,
                             @RequestParam("startPrice") BigDecimal startPrice,
                             @RequestParam("startTime") String startTime,
                             @RequestParam("endTime") String endTime) throws IOException {

        Image image = imgRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Картина не найдена"));
        image.setNameImage(name);
        image.setAuthor(author);
        image.setWidth(width);
        image.setHeight(height);
        image.setGenres(genres);

        Auction auction = auctionRepository.findByImageId(id).orElseThrow(()->
                new ResourceNotFoundException("Аукцион не найден"));
        auction.setStartPrice(startPrice);
        auction.setStartTime(LocalDateTime.parse(startTime));
        auction.setEndTime(LocalDateTime.parse(endTime));
        auctionRepository.save(auction);

        if (file != null && !file.isEmpty()) {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("images/" + filename);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            image.setPathImage("/images/" + filename);
        }

        return imgRepository.save(image);
    }

    @GetMapping("/owner/{imageId}")
    public Long getOwner(@PathVariable Long imageId) {
        return imgRepository.findById(imageId).orElseThrow(() -> new ResourceNotFoundException("Картина не найдена"))
                .getUser().getId();
    }
}