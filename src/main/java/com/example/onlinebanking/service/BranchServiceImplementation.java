package com.example.onlinebanking.service;

import com.example.onlinebanking.dao.BranchRepository;
import com.example.onlinebanking.domain.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImplementation implements BranchService {

    @Autowired
    BranchRepository branchRepository;

    @Override
    public Branch saveBranch(Branch branch) {

        return branchRepository.save(branch);
    }

    @Override
    public Branch findById(Long branchId) {

        return branchRepository.findById(branchId).orElse(null);
    }

    @Override
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    public void deleteById(Long branchId) {
        branchRepository.deleteById(branchId);
    }

    @Override
    public Branch updateBranch(Long branchId) {
        Branch existingBranch = branchRepository.findById(branchId).orElse(null);
        //need to think about the way to update branch
        return null;
    }
}
