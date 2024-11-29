package com.fit.iuh.controllers;

import com.fit.iuh.entites.Post;
import com.fit.iuh.entites.Topic;
import com.fit.iuh.enums.PostState;
import com.fit.iuh.services.PostService;
import com.fit.iuh.services.TopicService;
import com.fit.iuh.services.UserService;
import com.fit.iuh.utilities.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private UserService userService;
    @GetMapping
    public String index() {
        return "views_user/blog";
    }



    @GetMapping("/write_blog_basic")
    public String writeBlog(Model model) {
        Post post = new Post();
        List<Topic> topics = topicService.findAll();
        model.addAttribute("topics", topics);
        model.addAttribute("post", post);
        return "test/write_blog_basic";
    }
    @PostMapping("/save")
    public String savePost(Post post) {
        Date now = new Date();
//        post.setContent("12313123");
        post.setCreatedAt(now);
        post.setUpdatedAt(now);
//        post.setDescription("Khong co");
        post.setUrl("https://viblo.asia/announcements/khao-sat-viblo-nhu-cau-phat-trien-su-nghiep-it-toan-cau-PAoJePaA41j");
        post.setState(PostState.PUBLISHED);
        post.setTotalComments(1);
        post.setTotalUpVote(1);
        post.setTotalDownVote(0);
        post.setTotalShare(0);
        post.setTotalView(10);
//        post.setTopic(topicService.findById(1));
        post.setAuthor(userService.findById(1));
        
        postService.save(post);
        return "redirect:/";
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam("keyword") String keyword) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", HttpStatus.OK.value());

        response.put("data", postService.search(keyword));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable String id, Model model) {
        model.addAttribute("currentUser", userService.findUserByEmail(
                SpringContext.getCurrentUserEmail()
        ));
        model.addAttribute("idPost", id.trim());

        Post post = postService.findByIdAndUrl(id.trim());
        if (post == null){
            return "redirect:/";
        }

        model.addAttribute("post", post);
        System.out.println("ID: " + id);
        return "views_user/view-post";
    }
    

}
