package com.homebudget.repository;

import com.homebudget.model.Icon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IconRepository extends JpaRepository<Icon, Long> {
}