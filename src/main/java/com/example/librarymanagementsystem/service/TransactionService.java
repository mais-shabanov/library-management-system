package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.request.CreateTransactionRequest;
import com.example.librarymanagementsystem.dto.response.AuthorDto;
import com.example.librarymanagementsystem.exception.BookNotFoundException;
import com.example.librarymanagementsystem.exception.TransactionNotFoundException;
import com.example.librarymanagementsystem.model.*;
import com.example.librarymanagementsystem.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final BookService bookService;
    private final DetailService titleService;

    public TransactionService(
            TransactionRepository transactionRepository,
            UserService userService,
            BookService bookService,
            DetailService titleService
    ) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.bookService = bookService;
        this.titleService = titleService;
    }

    @Transactional
    public void createTransaction(CreateTransactionRequest createTransactionRequest) {

        Book book = bookService.findBookByIsbn(createTransactionRequest.isbn());
        book.getTransactions().stream().forEach(
                b -> {
                    if (b.getIsReturned() == false) {
                        throw new BookNotFoundException("Book can't be found");
                    }
                }
        );

        Detail detail = book.getDetail();
        Integer unitInStock = detail.getUnitsInStock();
        detail.setUnitsInStock(--unitInStock);
        User user = userService.findByFinCode(createTransactionRequest.finCode());
        Transaction transaction = new Transaction(
                user,
                book,
                false,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(createTransactionRequest.days())
        );
        transactionRepository.save(transaction);
    }

    @Transactional
    public void changeStatus(Long transactionId, String isbn) {
        Transaction transaction = findTransactionById(transactionId);
        Book book = bookService.findBookByIsbn(isbn);
        Detail detail = book.getDetail();
        Integer unitInStock = detail.getUnitsInStock();
        if (transaction.getIsReturned() == false) {
            detail.setUnitsInStock(++unitInStock);
            transaction.setIsReturned(true);
            transaction.setReturnDate(LocalDateTime.now());
            transactionRepository.save(transaction);
        }
    }

    public Integer getByIssueDateCount(LocalDate date){
        return transactionRepository.findByIssueDate(date).size();
    }

    public Integer getByReturnDateCount(LocalDate date){
        return transactionRepository.findByReturnDate(date).size();
    }

    public Set<AuthorDto> getMostSellingAuthors() {
            Set<Object[]> results = transactionRepository.findMostSellingAuthors();
            Set<Author> authors = new HashSet<>();
            for (Object[] result : results) {
                Author author = new Author();
                author.setId((Long) result[0]);
                author.setName((String) result[1]);
                authors.add(author);
            }
            return authors.stream().map(AuthorDto::convert).collect(toSet());
    }

    private Transaction findTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction can't be found with id : " + transactionId));
    }

}
