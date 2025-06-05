package com.homebudget.controller;

import com.homebudget.model.Category;
import com.homebudget.repository.CategoryRepository;
import com.homebudget.repository.FutureExpenseRepository;
import com.homebudget.repository.IconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private IconRepository iconRepository;
    @Autowired
    private FutureExpenseRepository expenseRepository;

    // Отображение списка категорий
    @GetMapping
    public String showCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories/categories";
    }

    // Отображение формы редактирования категорий
    @GetMapping("/edit")
    public String showEditCategoriesForm(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("icons", iconRepository.findAll());
        model.addAttribute("newCategory", new Category());
        return "categories/edit-categories";
    }

    // Добавление новой категории
    @PostMapping("/add")
    public String addCategory(
            @ModelAttribute("newCategory") @Validated Category newCategory,
            @RequestParam Long iconId,
            BindingResult result) {
        if (result.hasErrors()) {
            return "categories/edit-categories";
        }
        newCategory.setIcon(iconRepository.findById(iconId).orElseThrow());
        newCategory.setName(newCategory.getDisplayName().toUpperCase().replace(" ", "_"));
        categoryRepository.save(newCategory);
        return "redirect:/categories/edit";
    }

    // Редактирование категории
    @PostMapping("/update/{id}")
    public String updateCategory(
            @PathVariable Long id,
            @RequestParam String displayName,
            @RequestParam Long iconId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        category.setDisplayName(displayName);
        category.setName(displayName.toUpperCase().replace(" ", "_"));
        category.setIcon(iconRepository.findById(iconId).orElseThrow());
        categoryRepository.save(category);
        return "redirect:/categories/edit";
    }

    // Удаление категории
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
        // Проверка, используется ли категория
        if (expenseRepository.countByCategoryId(id) > 0) {
            // Можно добавить сообщение об ошибке через RedirectAttributes
            return "redirect:/categories/edit?error=categoryInUse";
        }
        categoryRepository.delete(category);
        return "redirect:/categories/edit";
    }
}
