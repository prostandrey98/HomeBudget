package com.homebudget.controller;

import com.homebudget.model.FutureExpense;
import com.homebudget.model.Transaction;
import com.homebudget.repository.CategoryRepository;
import com.homebudget.repository.FutureExpenseRepository;
import com.homebudget.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private FutureExpenseRepository futureExpenseRepository;

    @PostConstruct
    public void fixExistingRecords() {
        List<FutureExpense> expenses = futureExpenseRepository.findAll();
        for (FutureExpense expense : expenses) {
            if (!expense.isRecurring() && expense.getFrequency() != null) {
                expense.setFrequency(null);
                futureExpenseRepository.save(expense);
                System.out.println("Fixed expense with ID " + expense.getId() + ": set frequency to null");
            }
        }
    }

    @GetMapping({"/", "/finances"})
    public String finances(Model model) {
        return "finances";
    }

    @PostMapping("/add-transaction")
    public String addTransaction(
            @RequestParam String type,
            @RequestParam double amount,
            @RequestParam String category,
            @RequestParam String date) {
        Transaction transaction = new Transaction(type, amount, category, LocalDate.parse(date));
        transactionRepository.save(transaction);
        return "redirect:/finances";
    }

//    @GetMapping("/future-expenses")
//    public String futureExpenses(Model model) {
//        try {
//            List<FutureExpense> futureExpenses = futureExpenseRepository.findAll();
//            System.out.println("Loaded future expenses: " + futureExpenses);
//            model.addAttribute("futureExpenses", futureExpenses);
//            return "future-expenses";
//        } catch (Exception e) {
//            System.err.println("Error in futureExpenses: " + e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }
//    }

    @GetMapping("/edit-future-expense/{id}")
    public String editFutureExpense(@PathVariable Long id, Model model) {
        FutureExpense expense = futureExpenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid expense Id:" + id));
        model.addAttribute("expense", expense);
        return "edit-future-expense";
    }

//    @PostMapping("/update-future-expense")
//    public String updateFutureExpense(
//            @RequestParam Long id,
//            @RequestParam String title,
//            @RequestParam Long categoryId,
//            @RequestParam String urgency,
//            @RequestParam String importance,
//            @RequestParam(required = false) Boolean isRecurring,
//            @RequestParam(required = false) String frequency,
//            @RequestParam double estimatedCost) {
//        FutureExpense expense = futureExpenseRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid expense Id:" + id));
//        expense.setTitle(title);
//        expense.setCategoryId(categoryId);
//        expense.setUrgency(urgency);
//        expense.setImportance(importance);
//        boolean recurring = isRecurring != null ? isRecurring : false;
//        expense.setRecurring(recurring);
//        expense.setFrequency(recurring ? frequency : null);
//        expense.setEstimatedCost(estimatedCost);
//        expense.setDate(LocalDate.now());
//        futureExpenseRepository.save(expense);
//        return "redirect:/future-expenses";
//    }

    @PostMapping("/mark-as-purchased/{id}")
    public String markAsPurchased(@PathVariable Long id) {
        futureExpenseRepository.deleteById(id);
        return "redirect:/future-expenses";
    }

    @PostMapping("/delete-future-expense/{id}")
    public String deleteFutureExpense(@PathVariable Long id) {
        futureExpenseRepository.deleteById(id);
        return "redirect:/future-expenses";
    }

    @GetMapping("/expenses")
    public String expenses() {
        return "expenses";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/income-history")
    public String incomeHistory() {
        return "income-history";
    }

    @GetMapping("/api/transactions")
    @ResponseBody
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    @PostMapping("/api/transactions")
    @ResponseBody
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        transaction.setDate(LocalDate.parse(transaction.getDate().toString()));
        return transactionRepository.save(transaction);
    }

    public String formatCategory(String category) {
        if (category == null) return "Неизвестно";
        return switch (category) {
            case "food" -> "Еда";
            case "housing" -> "Жилье";
            case "entertainment" -> "Развлечения";
            case "children" -> "Дети";
            case "car" -> "Машина";
            case "clothing" -> "Вещи";
            case "other" -> "Другое";
            default -> "Неизвестно";
        };
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