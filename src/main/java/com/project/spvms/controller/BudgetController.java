package com.project.spvms.controller;


import com.project.spvms.entity.Budget;
import com.project.spvms.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetRepository budgetRepository;

    //  CREATE budget (Postman)
    @PostMapping
    public Budget createBudget(@RequestBody Budget budget) {
        return budgetRepository.save(budget);
    }

    //  GET all budgets
    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }
}
