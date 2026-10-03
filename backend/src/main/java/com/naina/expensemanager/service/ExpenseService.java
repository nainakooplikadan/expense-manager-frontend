package com.naina.expensemanager.service;

import java.util.List;
import com.naina.expensemanager.dto.ExpenseResponse;
import org.springframework.stereotype.Service;

import com.naina.expensemanager.exception.ResourceNotFoundException;

import com.naina.expensemanager.dto.ExpenseRequest;
import com.naina.expensemanager.entity.Expense;
import com.naina.expensemanager.entity.User;
import com.naina.expensemanager.repository.ExpenseRepository;
import com.naina.expensemanager.repository.UserRepository;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public ExpenseResponse addExpense(ExpenseRequest request) {

        Expense expense = new Expense();

        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        expense.setUser(user);

        Expense savedExpense = expenseRepository.save(expense);

        return toResponse(savedExpense);
        
    }
    public List<ExpenseResponse> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
    public List<ExpenseResponse> getExpensesByUser(Long userId) {
        return expenseRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }
    public ExpenseResponse getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        return toResponse(expense);
    }
    public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {

        Expense expense = expenseRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());

        Expense updatedExpense = expenseRepository.save(expense);

        return toResponse(updatedExpense);
    }
    public void deleteExpense(Long id) {

        Expense expense = expenseRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expenseRepository.delete(expense);
    }
    private ExpenseResponse toResponse(Expense expense) {

        ExpenseResponse response = new ExpenseResponse();

        response.setId(expense.getId());
        response.setDescription(expense.getDescription());
        response.setAmount(expense.getAmount());
        response.setCategory(expense.getCategory());
        response.setDate(expense.getDate());

        if (expense.getUser() != null) {
            response.setUserId(expense.getUser().getId());
        }

        return response;
    }
}