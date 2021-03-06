package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.*;
import com.blog.reviewwebsite.services.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ContentOrderMap orderMap;
    private ReviewService reviewService;
    private CategoryService categoryService;
    private FileService fileService;
    private ScoreService scoreService;

    public UserController(UserService userService, ContentOrderMap orderMap, ReviewService reviewService, CategoryService categoryService, FileService fileService, ScoreService scoreService) {
        this.userService = userService;
        this.orderMap = orderMap;
        this.reviewService = reviewService;
        this.categoryService = categoryService;
        this.fileService = fileService;
        this.scoreService = scoreService;
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Long id, Model model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "DEFAULT") OrderType reviewOrderType, @RequestParam(defaultValue = "DEFAULT") OrderType commentOrderType) {
        User user = userService.getUser(id);
        orderMap.mapReviewsByUserToOrderType(pageNumber, user);
        orderMap.mapCommentsByUserToOrderType(pageNumber, user);
        Page<Review> reviews = orderMap.reviewsByOrderTypeAndUser.get(reviewOrderType);
        Page<Comment> comments = orderMap.commentsByOrderTypeAndUser.get(commentOrderType);
        Page<Category> categories = categoryService.getAllCategoriesUserFollows(user, pageNumber);
        Set<Review> bookmarkedReviews = reviewService.getAllBookmarkedReviewsByUser(user);
        long followedCategoriesCount = categoryService.getAllCategoriesUserFollows(user, pageNumber).getTotalElements();
        int pageCount = reviewService.findAllReviewsByReviewer(pageNumber, user).getTotalPages();

        model.addAttribute("file", fileService.getUsersProfilePictureConvertedToString(user));
        model.addAttribute("orderType", reviewOrderType);
        model.addAttribute("postUpvotes", scoreService.howManyTimesThisUsersPostsHaveBeenUpvoted(user));
        model.addAttribute("commentUpvotes", scoreService.howManyTimesThisUsersCommentsHaveBeenUpvoted(user));
        model.addAttribute("categories", categories);
        model.addAttribute("followedCategoriesCount", followedCategoriesCount);
        model.addAttribute("incognito", user.isIncognito());
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNextPage", reviews.hasNext());
        model.addAttribute("reviewCount", reviews.getContent().size());
        model.addAttribute("commentCount", comments.getContent().size());
        model.addAttribute("reviews", reviews);
        model.addAttribute("bookmarkedReviews", bookmarkedReviews);
        model.addAttribute("bookmarkedReviewsCount", bookmarkedReviews.size());
        return "user";
    }

    @PostMapping("/incognito/toggle/{id}")
    public String toggleIncognito(@PathVariable Long id) {
        userService.toggleIncognito(id);
        return "redirect:/user/user/" + id;
    }

    @PostMapping("{id}/submit/profile-description")
    public String submitProfileDescription(@ModelAttribute User user) {
        userService.updateUserDescription(user);
        return "redirect:/user/user/" + user.getId();
    }

    @PostMapping("/submit")
    public String submitUser(@Valid @ModelAttribute("user") UserDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "signup";
        } else {
            userService.createUser(user);
            return "redirect:/login";
        }
    }

    @GetMapping("/signup")
    public String createUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "signup";
    }

    @GetMapping("/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/reviews";
    }

}
