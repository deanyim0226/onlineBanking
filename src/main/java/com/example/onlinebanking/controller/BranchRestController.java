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
import org.springframework.validation.FieldError;
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
        StringBuilder sb = new StringBuilder("");

        if(retrievedBranch != null || br.hasErrors()){

            if(br.hasErrors()){
                //error while entering account info from user
                List<FieldError> fieldErrors = br.getFieldErrors();
                for(FieldError fieldError: fieldErrors){
                    sb = sb.append("\""+fieldError.getField() +"\":"+fieldError.getDefaultMessage()+"\n");
                }
                System.out.println("sb: " + sb );
                headers.add("errors",sb.toString());

                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(retrievedBranch);
            }else{
                //user does not exist
                sb.append("Branch already exist");
                headers.add("errors",sb.toString());
                return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).body(retrievedBranch);
            }
        }

        Branch newBranch = branchService.saveBranch(branch);
        headers.add("saved Branch",newBranch.getBranchName());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(newBranch);
    }

    @PutMapping(value = "/updateBranch", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Branch> updateBranch(@Valid @RequestBody Branch branch, BindingResult br){

        Branch retrievedBranch = branchService.findById(branch.getBranchId());
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder("");

        if(retrievedBranch == null || br.hasErrors()){

            if(br.hasErrors()){
                //error while entering account info from user
                List<FieldError> fieldErrors = br.getFieldErrors();
                for(FieldError fieldError: fieldErrors){
                    sb = sb.append("\""+fieldError.getField() +"\":"+fieldError.getDefaultMessage()+"\n");
                }
                System.out.println("sb: " + sb );
                headers.add("errors",sb.toString());

                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).headers(headers).body(retrievedBranch);
            }else{
                //user does not exist
                sb.append("Branch does not exist");
                headers.add("errors",sb.toString());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(retrievedBranch);
            }
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
        HttpHeaders headers = new HttpHeaders();
        StringBuilder sb = new StringBuilder("");

        if(retrievedBranch == null){
            //branch does not exist
            sb.append("Branch does not exist");
            headers.add("errors", sb.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(retrievedBranch);
        }

        branchService.deleteById(branchId);
        headers.add("deleted branch", retrievedBranch.getBranchName());

        return ResponseEntity.status(HttpStatus.OK).body(retrievedBranch);
    }

}
