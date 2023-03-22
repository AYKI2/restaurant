package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public SimpleResponse save(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        repository.save(category);
        return new SimpleResponse("SAVE","Category successfully saved!");
    }

    @Override
    public Category finById(Long restaurantId, Long categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() ->
                        new NoSuchElementException("Category with id: " + categoryId + " not found!"));
    }

    @Override
    public SimpleResponse update(Long restaurantId, Long categoryId, CategoryRequest request) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() ->
                        new NoSuchElementException("Category with id: " + categoryId + " not found!"));
        if(repository.existsById(categoryId)) {
            category.setName(request.name());
            return new SimpleResponse("UPDATE", "Category successfully updated!");
        }
        return new SimpleResponse("FAIL", "Category failed to update!");
    }

    @Override
    public SimpleResponse delete(Long restaurantId, Long categoryId) {
        if(repository.existsById(categoryId)){
            repository.deleteById(categoryId);
            return new SimpleResponse("DELETE", "Category successfully updated!");
        }
        return new SimpleResponse("FAIL", "Category failed to update!");
    }
}
