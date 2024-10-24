package com.seokho.snsboard.controller;

import com.seokho.snsboard.model.Post;
import com.seokho.snsboard.model.PostPatchRequestBody;
import com.seokho.snsboard.model.PostPostRequestBody;
import com.seokho.snsboard.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        var posts = postService.getPosts();

        return ResponseEntity.ok(posts);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostByPostId(@PathVariable Long postId) {
        Optional<Post> mathcingPost = postService.getPostByPostId(postId);

        return mathcingPost.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostPostRequestBody postPostRequestBody) {
        var post = postService.createPost(postPostRequestBody);
        return ResponseEntity.ok(post);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostPatchRequestBody postPatchRequestBody) {

        var Post = postService.updatePost(postId, postPatchRequestBody);
        return ResponseEntity.ok(Post);

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletoPost(@PathVariable Long postId) {

        postService.deletePost(postId);
        return ResponseEntity.noContent().build();


    }
}