package com.homebudget.repository;

import com.homebudget.model.FutureExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FutureExpenseRepository extends JpaRepository<FutureExpense, Long> {
}