package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.response.BookDto;
import com.example.librarymanagementsystem.dto.response.UserDto;
import com.example.librarymanagementsystem.exception.UserNotFoundException;
import com.example.librarymanagementsystem.model.*;
import com.example.librarymanagementsystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DetailService detailService;
    private final PublisherService publisherService;

    public UserService(
            UserRepository userRepository,
            DetailService detailService, PublisherService publisherService) {
        this.userRepository = userRepository;
        this.detailService = detailService;
        this.publisherService = publisherService;
    }

    public User findByFinCode(String finCode) {
        return userRepository.findByFinCode(finCode)
                .orElseThrow(() -> new UserNotFoundException("User can't be found with fin code :" + finCode));
    }

    public Boolean existsByFinCode(String finCode) {
        return userRepository.existsByFinCode(finCode);
    }

    public Set<BookDto> getGivenBooksByFinCode(String finCode) {
        Set<Object[]> results = userRepository.findGivenBooksByFinCode(finCode);
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


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + email));
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Set<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::convert)
                .collect(toSet());
    }

    public UserDto getUserById(Long id) {
        return UserDto.convert(findById(id));
    }

    protected User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User can't be found with by id : " + id));
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }
}
