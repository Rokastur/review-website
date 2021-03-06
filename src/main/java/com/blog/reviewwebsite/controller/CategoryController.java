package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;
    private ContentOrderMap orderMap;

    public CategoryController(CategoryService categoryService, ContentOrderMap orderMap) {
        this.categoryService = categoryService;
        this.orderMap = orderMap;
    }

    @GetMapping("/all")
    public String getAllCategories(@RequestParam(defaultValue = "0") int pageNumber, Model model, @RequestParam(defaultValue = "DEFAULT") OrderType categoriesOrderType) {
        orderMap.mapCategoriesToOrderType(pageNumber);
        Page<Category> categories = orderMap.categoriesByOrderType.get(categoriesOrderType);
        int pageCount = categoryService.getAllCategories(pageNumber).getTotalPages();
        model.addAttribute("orderType", categoriesOrderType);
        model.addAttribute("categories", categories.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNextPage", categories.hasNext());
        model.addAttribute("pageCount", pageCount);
        return "categories";
    }

    @PostMapping("/new/submit")
    public String submitNewCategory(@ModelAttribute Category category, @AuthenticationPrincipal User user) {
        categoryService.updateOrSaveCategory(category, user);
        return "redirect:/categories/all";
    }

    @PostMapping("/follow/{id}")
    public String followCategory(@PathVariable Long id, @AuthenticationPrincipal User user) {
        categoryService.followCategory(user, id);
        return "redirect:/categories/all";
    }

    @GetMapping("/new")
    public String createNewCategory(Model model) {
        model.addAttribute("category", new Category());
        return "categoryForm";
    }
}
