package cz.uhk.pro2_d.controller;

import cz.uhk.pro2_d.model.Category;
import cz.uhk.pro2_d.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories_list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("category", new Category());
        return "categories_add";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable long id) {
        model.addAttribute("category", categoryService.getCategory(id));
        return "categories_add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/categories/";
    }

    @GetMapping("/{id}/delete")
    public String deleteConfirm(Model model, @PathVariable long id) {
        model.addAttribute("category", categoryService.getCategory(id));
        return "categories_delete";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories/";
    }
}
