package com.example.onlinebanking.service;

import com.example.onlinebanking.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchService{

    /*
    , create, read, update, and delete
     */
    public Branch saveBranch(Branch branch);

    public Branch findById(Long branchId);

    public List<Branch> findAll();

    public void deleteById(Long branchId);

    public Branch updateBranch(Long branchId);


}
