package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.request.CreateAuthorRequest;
import com.example.librarymanagementsystem.dto.request.UpdateAuthorRequest;
import com.example.librarymanagementsystem.dto.response.AuthorDto;
import com.example.librarymanagementsystem.exception.AuthorNotFoundException;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.Detail;
import com.example.librarymanagementsystem.repository.AuthorRepository;
import com.example.librarymanagementsystem.repository.DetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import static java.util.stream.Collectors.toSet;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public void createAuthor(CreateAuthorRequest createAuthorRequest) {
        Author author = new Author(createAuthorRequest.name());
        authorRepository.save(author);
    }

    @Transactional
    public void updateAuthor(UpdateAuthorRequest updateAuthorRequest) {
        Author author = findAuthorById(updateAuthorRequest.id());
        author.setName(updateAuthorRequest.name());
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Author author = findAuthorById(id);
        Set<Detail> details = author.getDetails();
        for (var detail : details) {
            detail.getAuthors().remove(author);
        }
        authorRepository.delete(author);
    }

    public AuthorDto getAuthorById(Long id) {
        Author author = findAuthorById(id);
        return AuthorDto.convert(author);
    }

    public Set<AuthorDto> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorDto::convert)
                .collect(toSet());
    }

    protected Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author can't be found with id : " + id));
    }
}
