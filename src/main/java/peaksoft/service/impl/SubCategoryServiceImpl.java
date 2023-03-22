package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.SubCategoryService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository repository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<SubCategory> getAll(Long restaurantId){
        return repository.findAll();
    }

    @Override
    public SimpleResponse save(Long restaurantId, SubCategoryRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NoSuchElementException("Category with id: " + request.categoryId() + " not found!"));
        SubCategory subCategory = new SubCategory();
        subCategory.setName(request.name());
        subCategory.setCategory(category);
        repository.save(subCategory);
        return new SimpleResponse("SAVE", "SubCategory successfully saved!");
    }

    @Override
    public SubCategory finById(Long restaurantId, Long subCategoryId) {
        return repository.findById(subCategoryId)
                .orElseThrow(() ->
                        new NoSuchElementException("SubCategory with id: " + subCategoryId + " not found!"));
    }

    @Override
    public SimpleResponse update(Long restaurantId, Long subCategoryId, SubCategoryRequest request) {
        SubCategory subCategory = repository.findById(subCategoryId)
                .orElseThrow(() -> new NoSuchElementException("SubCategory with id: " + subCategoryId + " not found!"));
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NoSuchElementException("Category with id: " + request.categoryId() + " not found!"));
        if(!repository.existsById(subCategoryId)) {
            return new SimpleResponse("FAIL","SubCategory with id: "+subCategory+" failed to update!");
        }
        subCategory.setName(request.name());
        subCategory.setCategory(category);
        return new SimpleResponse("UPDATE", "SubCategory with id: " + subCategoryId + " successfully updated!");
    }

    @Override
    public SimpleResponse delete(Long restaurantId, Long subCategoryId) {
        if(repository.existsById(subCategoryId)) {
            repository.deleteById(subCategoryId);
            return new SimpleResponse("DELETE", "SubCategory with id: " + subCategoryId + " successfully deleted!");
        }
        return new SimpleResponse("FAIL","SubCategory with id: "+subCategoryId+" failed to delete!");
    }
}
