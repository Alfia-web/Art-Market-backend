package controller;

import com.example.demo.entity.Comment;
import exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.CommentRepository;
import repository.ImageRepository;
import com.example.demo.entity.Image;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentRepository commentRepo;
    private final ImageRepository imgRepo;

    public CommentController(CommentRepository commentRepo, ImageRepository imgRepo) {
        this.commentRepo = commentRepo;
        this.imgRepo = imgRepo;
    }

    @GetMapping("/image/{imageId}")
    public List<Comment> getComment(@PathVariable Long imageId){
        return commentRepo.findByImageIdOrderByAddAtDesc(imageId);
    }

    @PostMapping
    public Comment addComment(@RequestParam Long imageId, @RequestParam Long userId, @RequestParam String username,
                              @RequestParam String text) {
        Image image = imgRepo.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Картина не найдена"));

        Comment comment = new Comment();
        comment.setImage(image);
        comment.setUserId(userId);
        comment.setUsername(username);
        comment.setText(text);
        comment.setAddAt(LocalDateTime.now());

        return commentRepo.save(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestParam Long userId){
        Comment comment=commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Комментарий не найден"));

        if(!comment.getUserId().equals(userId)){
            return ResponseEntity.status(403).build();
        }

        commentRepo.delete(comment);
        return ResponseEntity.noContent().build();
    }
}
