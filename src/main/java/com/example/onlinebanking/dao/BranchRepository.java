package com.example.onlinebanking.dao;

import com.example.onlinebanking.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch,Long> {
}
