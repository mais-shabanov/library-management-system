package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.request.CreateCategoryRequest;
import com.example.librarymanagementsystem.dto.request.UpdateCategoryRequest;
import com.example.librarymanagementsystem.dto.response.CategoryDto;
import com.example.librarymanagementsystem.exception.CategoryNotFoundException;
import com.example.librarymanagementsystem.model.Book;
import com.example.librarymanagementsystem.model.Category;
import com.example.librarymanagementsystem.model.Detail;
import com.example.librarymanagementsystem.repository.CategoryRepository;
import com.example.librarymanagementsystem.repository.DetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import static java.util.stream.Collectors.toSet;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Set<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::convert)
                .collect(toSet());
    }

//    public CategoryDto getCategoryByName(String name) {
//        return categoryDtoConverter.convertToCategoryDto(
//                categoryRepository.findCategoryByName(name).orElseThrow(() -> new CategoryNotFoundException("Category can't be found with name : " + name))
//        );
//    }

    public CategoryDto getCategoryById(Long id) {
        return CategoryDto.convert(findCategoryById(id));
    }

    @Transactional

    public void createCategory(CreateCategoryRequest createCategoryRequest) {
        Category category = new Category(
                createCategoryRequest.name()
        );
        categoryRepository.save(category);
    }

    @Transactional
    public void updateCategory(UpdateCategoryRequest updateCategoryRequest) {
        Category category = findCategoryById(updateCategoryRequest.id());
        category.setName(updateCategoryRequest.name());
    }

    @Transactional

    public void deleteCategory(Long id) {
        Category category = findCategoryById(id);
        Set<Detail> details = category.getDetails();
        for (var detail : details) {
            detail.getCategories().remove(category);
        }
        categoryRepository.delete(category);
    }

    protected Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category can't be found with id : " + id));
    }


}
