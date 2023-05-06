package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.request.CreateTransactionRequest;
import com.example.librarymanagementsystem.dto.response.AuthorDto;
import com.example.librarymanagementsystem.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest) {
        transactionService.createTransaction(createTransactionRequest);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> changeStatus(@RequestParam Long transactionId, String isbn) {
        transactionService.changeStatus(transactionId, isbn);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/issue/date")
    public ResponseEntity<Integer> getByIssueDateCount(@RequestParam("date")
                                                        @DateTimeFormat(pattern = "YYYY-MM-dd") LocalDate date){
        return ResponseEntity.ok(transactionService.getByIssueDateCount(date));
    }

    @GetMapping("/return/date")
    public ResponseEntity<Integer> getByReturnDateCount(@RequestParam("date")
                                                        @DateTimeFormat(pattern = "YYYY-MM-dd") LocalDate date){
        return ResponseEntity.ok(transactionService.getByReturnDateCount(date));
    }

    @GetMapping("/most-selling-authors")
    public ResponseEntity<Set<AuthorDto>> getMostSellingAuthors(){
        return ResponseEntity.ok(transactionService.getMostSellingAuthors());
    }
}
