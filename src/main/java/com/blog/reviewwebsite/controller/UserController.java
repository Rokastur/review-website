package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String createUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/submit")
    public String submitUser(User user, Model model) {
        User newUser = userService.updateOrSaveUser(user);
        model.addAttribute("user", newUser);
        return "redirect:/reviews";
    }

    @GetMapping("/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/reviews";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/reviews";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> userList = userService.getUsers();
        model.addAttribute("users", userList);
        return "users";
    }
}
