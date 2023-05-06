package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.request.CreateDetailRequest;
import com.example.librarymanagementsystem.dto.request.UpdateDetailRequest;
import com.example.librarymanagementsystem.dto.response.DetailDto;
import com.example.librarymanagementsystem.exception.TitleNotFoundException;
import com.example.librarymanagementsystem.model.Author;
import com.example.librarymanagementsystem.model.Category;
import com.example.librarymanagementsystem.model.Detail;
import com.example.librarymanagementsystem.repository.DetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

@Service
public class DetailService {

    private final DetailRepository detailRepository;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    public DetailService(DetailRepository detailRepository, CategoryService categoryService, AuthorService authorService) {
        this.detailRepository = detailRepository;
        this.categoryService = categoryService;
        this.authorService = authorService;
    }


    public DetailDto getDetailById(Long id) {
        Detail detail = findDetailById(id);
        return DetailDto.convert((detail));
    }

    public Set<DetailDto> getAllDetails() {
        return detailRepository.findAll()
                .stream()
                .map(DetailDto::convert)
                .collect(toSet());
    }

    @Transactional
    public void createTitle(CreateDetailRequest createDetailRequest) {

        Set<Long> categoryIds = createDetailRequest.categoryIds();
        Set<Category> categories = new HashSet<>();
        for (var categoryId : categoryIds) {
            categories.add(categoryService.findCategoryById(categoryId));
        }
        Set<Long> authorIds = createDetailRequest.authorIds();
        Set<Author> authors = new HashSet<>();
        for (var authorId : authorIds) {
            authors.add(authorService.findAuthorById(authorId));
        }
            Detail detail = new Detail(
                    createDetailRequest.title(),
                    createDetailRequest.description(),
                    createDetailRequest.unitsInStock(),
                    authors,
                    categories
            );
            detailRepository.save(detail);
        }

    @Transactional
    public void updateDetail(UpdateDetailRequest updateDetailRequest) {
        Detail detail = findDetailById(updateDetailRequest.id());
        detail.setTitle(updateDetailRequest.title());
        detail.setDescription(updateDetailRequest.description());
        detail.setUnitsInStock(detail.getUnitsInStock() + updateDetailRequest.unitsInStock());
    }

    protected Detail findDetailById(Long id) {
        return detailRepository.findById(id)
                .orElseThrow(() -> new TitleNotFoundException("Title can't be found with id : " + id));
    }

    public Set<Detail> findByCategoryNames(Set<String> categoryNames) {
        return detailRepository.findByCategories_NameIn(categoryNames);
    }
}
