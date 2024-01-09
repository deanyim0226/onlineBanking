package com.example.onlinebanking.controller;


import com.example.onlinebanking.domain.Branch;
import com.example.onlinebanking.service.BranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("r")
public class BranchRestController {

    @Autowired
    BranchService branchService;

    @GetMapping(value = "/getBranches", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Branch>> getBranches(){

        List<Branch> retrievedBranches = branchService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(retrievedBranches);
    }

    @PostMapping(value = "/saveBranch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Branch> saveBranch(@Valid @RequestBody Branch branch, BindingResult br){

        Branch retrievedBranch = branchService.findById(branch.getBranchId());
        HttpHeaders headers = new HttpHeaders();

        if(retrievedBranch != null || br.hasErrors()){

            if(br.hasErrors()){
                //error while entering the branch info from user
                return null;
            }

            //duplicate branch is found
            return null;
        }

        Branch newBranch = branchService.saveBranch(branch);
        headers.add("saved Branch",newBranch.getBranchName());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(newBranch);
    }

    @PutMapping(value = "/updateBranch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Branch> updateBranch(@Valid @RequestBody Branch branch, BindingResult br){

        Branch retrievedBranch = branchService.findById(branch.getBranchId());
        HttpHeaders headers = new HttpHeaders();


        if(retrievedBranch == null || br.hasErrors()){

            if(br.hasErrors()){
                //error while entering the branch info from user
                return null;
            }

            //user does not exist
            return null;
        }

        retrievedBranch.setBranchName(branch.getBranchName());
        retrievedBranch.setAddress(branch.getAddress());
        branchService.saveBranch(retrievedBranch);

        headers.add("updated Branch", retrievedBranch.getBranchName());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(retrievedBranch);

    }

    @DeleteMapping(value = "/deleteBranch/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Branch> deleteBranch(@PathVariable Long branchId){

        Branch retrievedBranch = branchService.findById(branchId);

        if(retrievedBranch == null){
            //branch does not exist
        }

        branchService.deleteById(branchId);
        return ResponseEntity.status(HttpStatus.OK).body(retrievedBranch);
    }

}
