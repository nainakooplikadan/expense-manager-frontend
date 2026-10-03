package com.naina.expensemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naina.expensemanager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);
}