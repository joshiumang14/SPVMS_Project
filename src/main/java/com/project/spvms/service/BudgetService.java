package com.project.spvms.service;

import com.project.spvms.entity.Budget;
import com.project.spvms.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    // Validate budget before PR submission
    public void validateBudget(String costCenter, Double amount) {

        Budget budget = budgetRepository.findByCostCenter(costCenter)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        double remaining =
                budget.getAllocatedAmount() - budget.getUtilizedAmount();

        if (amount > remaining) {
            throw new RuntimeException("Budget exceeded for cost center");
        }
    }

    // Update utilized budget after approval
    public void updateUtilizedAmount(String costCenter, Double amount) {

        Budget budget = budgetRepository.findByCostCenter(costCenter)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        budget.setUtilizedAmount(
                budget.getUtilizedAmount() + amount
        );

        budgetRepository.save(budget);
    }
}
