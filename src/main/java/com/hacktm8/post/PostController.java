package com.hacktm8.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class PostController {

    private String URI =  "https://jsonplaceholder.typicode.com";

    @GetMapping("/posts")
    public Post[] getPosts() {
        RestTemplate templ = new RestTemplate();
        ResponseEntity<Post[]> maybePost = templ.getForEntity(URI + "/posts", Post[].class);
        return maybePost.getBody();
    }

    @GetMapping("/posts/{id}")
    public Post getPostById(@PathVariable int id) {
        RestTemplate templ = new RestTemplate();
        ResponseEntity<Post> maybePost = templ.getForEntity(URI + "/posts/" + id, Post.class);
        return maybePost.getBody();
    }
}
