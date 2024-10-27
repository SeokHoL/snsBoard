package com.seokho.snsboard.controller;

import com.seokho.snsboard.model.entity.UserEntity;
import com.seokho.snsboard.model.post.Post;
import com.seokho.snsboard.model.post.PostPatchRequestBody;
import com.seokho.snsboard.model.post.PostPostRequestBody;
import com.seokho.snsboard.model.user.User;
import com.seokho.snsboard.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        logger.info("GET /api/v1/posts");
        var posts = postService.getPosts();

        return ResponseEntity.ok(posts);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostByPostId(@PathVariable Long postId) {
        logger.info("GET /api/v1/posts/{}");
        var post = postService.getPostByPostId(postId);

        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostPostRequestBody postPostRequestBody,
                                           Authentication authentication) {
        logger.info("POST /api/v1/posts");
        var post = postService.createPost(postPostRequestBody,(UserEntity)authentication.getPrincipal());
        return ResponseEntity.ok(post);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostPatchRequestBody postPatchRequestBody,
                                           Authentication authentication) {
        logger.info("PATCH /api/v1/posts/{}");
        var Post = postService.updatePost(postId, postPatchRequestBody,(UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(Post);

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletoPost(@PathVariable Long postId,
                                           Authentication authentication) {
        logger.info("DELETE /api/v1/posts/{}");
        postService.deletePost(postId, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<Post> toggleLike(@PathVariable Long postId,
                                           Authentication authentication) {

        var post = postService.toggleLike(postId, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(post);
    }
}