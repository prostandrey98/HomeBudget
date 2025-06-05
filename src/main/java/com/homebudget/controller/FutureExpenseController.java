package com.homebudget.controller;

import com.homebudget.model.FutureExpense;
import com.homebudget.repository.CategoryRepository;
import com.homebudget.repository.FutureExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class FutureExpenseController {
    @Autowired
    private FutureExpenseRepository expenseRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FutureExpenseRepository futureExpenseRepository;

    @GetMapping("/future-expenses")
    public String showFutureExpenses(Model model) {
        model.addAttribute("futureExpenses", expenseRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "future-expenses";
    }

    @GetMapping("/edit-future-expense/{id}")
    public String showEditFutureExpenseForm(@PathVariable Long id, Model model) {
        FutureExpense expense = futureExpenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid expense ID: " + id));
        model.addAttribute("expense", expense);
        model.addAttribute("categories", categoryRepository.findAll());
        return "edit-future-expense"; // Используем тот же шаблон
    }

//    @GetMapping("/edit-future-expense/{id}")
//    public String editFutureExpense(@PathVariable Long id, Model model) {
//        FutureExpense expense = futureExpenseRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid expense Id:" + id));
//        model.addAttribute("expense", expense);
//        return "edit-future-expenses";
//    }

    // Сохранение изменений
    @PostMapping("/edit-future-expense/{id}")
    public String updateFutureExpense(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam Long categoryId,
            @RequestParam String urgency,
            @RequestParam String importance,
            @RequestParam(required = false, defaultValue = "false") Boolean isRecurring,
            @RequestParam(required = false) String frequency,
            @RequestParam BigDecimal estimatedCost) {
        FutureExpense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid expense ID: " + id));
        expense.setTitle(title);
        expense.setCategory(categoryRepository.findById(categoryId).orElseThrow());
        expense.setUrgency(FutureExpense.Urgency.valueOf(urgency));
        expense.setImportance(FutureExpense.Importance.valueOf(importance));
        expense.setIsRecurring(isRecurring);
        if (isRecurring && frequency != null && !frequency.isEmpty()) {
            try {
                expense.setFrequency(FutureExpense.Frequency.valueOf(frequency));
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid frequency value: " + frequency);
            }
        } else {
            expense.setFrequency(null); // Сбрасываем частоту, если не периодический
        }
        expense.setEstimatedCost(estimatedCost);
        expenseRepository.save(expense);
        return "redirect:/future-expenses";
    }

    @PostMapping("/add-future-expense")
    public String addFutureExpense(
            @RequestParam String title,
            @RequestParam Long categoryId,
            @RequestParam String urgency,
            @RequestParam String importance,
            @RequestParam boolean isRecurring,
            @RequestParam(required = false) String frequency,
            @RequestParam BigDecimal estimatedCost) {
        FutureExpense expense = new FutureExpense();
        expense.setTitle(title);
        expense.setCategory(categoryRepository.findById(categoryId).orElseThrow());
        expense.setUrgency(FutureExpense.Urgency.valueOf(urgency.toUpperCase()));
        expense.setImportance(FutureExpense.Importance.valueOf(importance.toUpperCase()));
        expense.setIsRecurring(isRecurring);
        if (isRecurring && frequency != null) {
            expense.setFrequency(FutureExpense.Frequency.valueOf(frequency.toUpperCase()));
        }
        expense.setEstimatedCost(estimatedCost);
        expenseRepository.save(expense);
        return "redirect:/future-expenses";
    }

    public String formatUrgency(String urgency) {
        if (urgency == null) return "Не срочно";
        return urgency.equals("urgent") ? "Срочно" : "Не срочно";
    }

    public String formatImportance(String importance) {
        if (importance == null) return "Не важно";
        return importance.equals("important") ? "Важно" : "Не важно";
    }

    public String formatFrequency(String frequency) {
        if (frequency == null) return "-";
        return switch (frequency) {
            case "weekly" -> "Еженедельно";
            case "monthly" -> "Ежемесячно";
            case "bimonthly" -> "Раз в 2 месяца";
            case "quarterly" -> "Ежеквартально";
            case "yearly" -> "Ежегодно";
            default -> "-";
        };
    }
}