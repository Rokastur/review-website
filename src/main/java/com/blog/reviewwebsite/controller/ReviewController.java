package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    private String getReviews(@RequestParam(defaultValue = "0") int pageNumber, Model model) {
        Page<Review> reviews = reviewService.getAllReviews(pageNumber);
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNextPage", reviews.hasNext());
        return "reviews";
    }

    @GetMapping("/form")
    private String createReview(Model model) {
        Review review = new Review();
        model.addAttribute("review", review);
        return "form";
    }

    @PostMapping("/submit")
    private String form(@ModelAttribute Review review, Model model){
        Review newReview = reviewService.updateOrSaveReview(review);
        model.addAttribute("review",newReview);
        return "redirect:/reviews";
    }

}