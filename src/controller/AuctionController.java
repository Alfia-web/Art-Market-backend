package controller;

import exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import repository.AuctionRepository;
import repository.RateRepository;
import com.example.demo.entity.Auction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entity.AuctionStatus;
import  com.example.demo.entity.Rate;
import repository.UserRepository;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {
    private final AuctionRepository auctionRepository;
    private final RateRepository rateRepository;
    private final UserRepository userRepository;

    public AuctionController(AuctionRepository auctionRepository,
                             RateRepository rateRepository, UserRepository userRepository) {
        this.auctionRepository = auctionRepository;
        this.rateRepository = rateRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<Auction> getByImage(@PathVariable Long imageId) {
        Auction auction = auctionRepository.findByImageId(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Аукцион не найден"));
        return ResponseEntity.ok(auction);
    }

    @PostMapping("/rate")
    public ResponseEntity<?> addRate(@RequestParam Long auctionId, @RequestParam Long userId, @RequestParam BigDecimal amount) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new ResourceNotFoundException("Аукцион не найден"));

        com.example.demo.entity.User user = userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("Пользователь не найден"));

        if (auction.getStatus() != AuctionStatus.ACTIVE) {
            return ResponseEntity.badRequest().body("Торги не активны");
        }
        else if (auction.getStatus() == AuctionStatus.ACTIVE && amount.compareTo(auction.getCurrentPrice()) <= 0) {
            return ResponseEntity.badRequest()
                    .body("Ставка должна быть выше текущей: " + auction.getCurrentPrice());
        }

        BigDecimal balance = user.getBalance() != null ? user.getBalance() : BigDecimal.ZERO;
        if(balance.compareTo(amount) < 0){
            return ResponseEntity.badRequest().body("Недостаточно средств на балансе");
        }

        Rate rate = new Rate();
        rate.setAuction(auction);
        rate.setUserId(userId);
        rate.setAmount(amount);
        rate.setAddAt(LocalDateTime.now());
        rateRepository.save(rate);

        auction.setCurrentPrice(amount);
        auction.setWinnerId(userId);
        auctionRepository.save(auction);

        return ResponseEntity.ok(auction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable Long id){
        auctionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Scheduled(fixedRate = 15000)
    public void updateStatuses() {
        LocalDateTime now = LocalDateTime.now();

        List<Auction> pending = auctionRepository.findByStatus(AuctionStatus.PENDING);

        for (Auction a : pending) {
            if (now.isAfter(a.getStartTime())) {
                a.setStatus(AuctionStatus.ACTIVE);
                auctionRepository.save(a);
            }
        }

        List<Auction> active = auctionRepository.findByStatus(AuctionStatus.ACTIVE);

        for (Auction a : active) {
            if (now.isAfter(a.getEndTime())) {
                a.setStatus(AuctionStatus.FINISHED);
                a.setFinalPrice(a.getCurrentPrice());
                auctionRepository.save(a);
            }
        }

    }
}

