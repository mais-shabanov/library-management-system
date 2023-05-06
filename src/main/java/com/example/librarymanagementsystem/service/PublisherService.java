package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.request.CreatePublisherRequest;
import com.example.librarymanagementsystem.dto.request.UpdatePublisherRequest;
import com.example.librarymanagementsystem.dto.response.PublisherDto;
import com.example.librarymanagementsystem.exception.PublisherNotFoundException;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Publisher;
import com.example.librarymanagementsystem.repository.BookRepository;
import com.example.librarymanagementsystem.repository.PublisherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import static java.util.stream.Collectors.toSet;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
    }

    protected Publisher findPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException("Publisher can't be found with id : " + id));
    }

    public Set<PublisherDto> getAllPublishers() {
        return publisherRepository.findAll()
                .stream()
                .map(PublisherDto::convert)
                .collect(toSet());
    }

    public PublisherDto getPublisherById(Long id) {
        return PublisherDto.convert(findPublisherById(id));
    }

    @Transactional

    public void createPublisher(CreatePublisherRequest createPublisherRequest) {
        Publisher publisher = new Publisher(
                createPublisherRequest.name()
        );
        publisherRepository.save(publisher);
    }

    @Transactional
    public void updatePublisher(UpdatePublisherRequest updatePublisherRequest) {
        Publisher publisher = findPublisherById(updatePublisherRequest.id());
        publisher.setName(updatePublisherRequest.name());
    }

    @Transactional
    public void deletePublisher(Long id) {
        Publisher publisher = findPublisherById(id);
        Set<Book> books = publisher.getBooks();
        for (Book book : books) {
            book.setPublisher(null);
        }
        publisherRepository.delete(publisher);
    }

}
