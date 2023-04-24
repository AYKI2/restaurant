package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    @Override
    public List<CategoryResponse> getAll() {
        return repository.getAll();
    }

    @Override
    public SimpleResponse save(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        repository.save(category);
        return new SimpleResponse("SAVE","Category successfully saved!");
    }

    @Override
    public CategoryResponse finById(Long categoryId) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() ->
                        new NotFoundException("Category with id: " + categoryId + " not found!"));
        return new CategoryResponse(category.getId(),category.getName());
    }

    @Override
    public SimpleResponse update(Long categoryId, CategoryRequest request) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() ->
                        new NotFoundException("Category with id: " + categoryId + " not found!"));
        if(repository.existsById(categoryId)) {
            category.setName(request.name());
            return new SimpleResponse("UPDATE", "Category successfully updated!");
        }
        return new SimpleResponse("FAIL", "Category failed to update!");
    }

    @Override
    public SimpleResponse delete(Long categoryId) {
        if(repository.existsById(categoryId)){
            repository.deleteById(categoryId);
            return new SimpleResponse("DELETE", "Category successfully deleted!");
        }
        return new SimpleResponse("FAIL", "Category failed to update!");
    }
}
