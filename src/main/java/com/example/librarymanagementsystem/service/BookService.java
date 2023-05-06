package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.request.CreateBookRequest;
import com.example.librarymanagementsystem.dto.request.UpdateBookRequest;
import com.example.librarymanagementsystem.dto.response.BookDto;
import com.example.librarymanagementsystem.exception.BookAlreadyExistsException;
import com.example.librarymanagementsystem.exception.BookNotFoundException;
import com.example.librarymanagementsystem.exception.CategoryNotFoundException;
import com.example.librarymanagementsystem.model.*;
import com.example.librarymanagementsystem.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final DetailService detailService;
    private final PublisherService publisherService;

    public BookService(
            BookRepository bookRepository,
            DetailService detailService,
            PublisherService publisherService
    ) {
        this.bookRepository = bookRepository;
        this.detailService = detailService;
        this.publisherService = publisherService;
    }

    public Set<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookDto::convert)
                .collect(toSet());
    }

    public BookDto getBookByIsbn(String isbn) {
        return BookDto.convert(
                bookRepository.findBookByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book can't be found with isbn : " + isbn))
        );
    }

    public BookDto getBookById(Long id) {
        return BookDto.convert(findBookById(id));
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book can't be found with id : " + id));
    }

    @Transactional
    public void createBook(CreateBookRequest createBookRequest) {

        if (bookRepository.findBookByIsbn(createBookRequest.isbn()).isPresent()) {
            throw new BookAlreadyExistsException("Book already exists with isbn : " + createBookRequest.isbn());
        }

        Detail detail = detailService.findDetailById(createBookRequest.detailId());
        Publisher publisher = publisherService.findPublisherById(createBookRequest.publisherId());
        Book book = new Book(
                createBookRequest.isbn(),
                detail,
                publisher
        );
        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(UpdateBookRequest updateBookRequest) {

        Book book = findBookById(updateBookRequest.id());
        Detail detail = detailService.findDetailById(updateBookRequest.detailId());
        Publisher publisher = publisherService.findPublisherById(updateBookRequest.publisherId());
        book.setIsbn(updateBookRequest.isbn());
        book.setDetail(detail);
        book.setPublisher(publisher);
    }

    @Transactional
    public void deleteBookById(Long id) {
        Book book = findBookById(id);
        bookRepository.delete(book);
    }

    public Set<BookDto> getAllBooksByCategoryNames(Set<String> categoryNames) {
        Set<Detail> details = detailService.findByCategoryNames(categoryNames);
        if (details.isEmpty()) {
            throw new CategoryNotFoundException("Categories can't be found with names : " + categoryNames);
        }
        Set<String> foundCategories = details.stream()
                .flatMap(detail -> detail.getCategories().stream())
                .map(Category::getName)
                .collect(toSet());
        for (String categoryName : categoryNames) {
            if (!foundCategories.contains(categoryName)) {
                throw new CategoryNotFoundException("Category can't be found with name : " + categoryName);
            }
        }

        Set<Book> books = new HashSet<>();
        for (var detail : details) {
            detail.getBooks().stream().forEach(book -> {
                books.add(book);
            });
        }
        return books.stream().map(BookDto::convert).collect(toSet());
    }

    public Book findBookByIsbn(String isbn) {
        return bookRepository.findBookByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book can't be found with isbn : " + isbn));
    }

    public Set<BookDto> getFamousBooks() {
        Set<Object[]> results = bookRepository.findFamousBooks();
        Set<Book> books = new HashSet<>();
        for (Object[] result : results) {
            Book book = new Book();
            book.setId((Long) result[0]);
            book.setIsbn((String) result[1]);
            Detail detail = detailService.findDetailById((Long) result[2]);
            book.setDetail(detail);
            Publisher publisher = publisherService.findPublisherById((Long) result[3]);
            book.setPublisher(publisher);
            books.add(book);
        }
        return books.stream().map(BookDto::convert).collect(toSet());
    }

}
