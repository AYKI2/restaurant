package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.SubCategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository repository;
    private final CategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<SubCategoryResponse> getAll(Long restaurantId){
        return repository.getAll();
    }

    @Override
    public List<SubCategoryResponse> getAllByCategory(Long categoryId, String ascOrDesc) {
        if(ascOrDesc.equals("asc")) {
            return repository.getAllByCategoryIdAsc(categoryId);
        }else {
            return repository.getAllByCategoryIdDesc(categoryId);
        }
    }

    @Override
    public SimpleResponse save(Long restaurantId, SubCategoryRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Category with id: " + request.categoryId() + " not found!"));
        SubCategory subCategory = new SubCategory();
        subCategory.setName(request.name());
        subCategory.setCategory(category);
        repository.save(subCategory);
        return new SimpleResponse("SAVE", "SubCategory successfully saved!");
    }

    @Override
    public SubCategoryResponse finById(Long restaurantId, Long subCategoryId) {
        SubCategory subCategory = repository.findById(subCategoryId)
                .orElseThrow(() ->
                        new NotFoundException("SubCategory with id: " + subCategoryId + " not found!"));
        return new SubCategoryResponse(subCategory.getId(), subCategory.getName(),subCategory.getCategory().getName());
    }

    @Override
    public SimpleResponse update(Long restaurantId, Long subCategoryId, SubCategoryRequest request) {
        SubCategory subCategory = repository.findById(subCategoryId)
                .orElseThrow(() -> new NotFoundException("SubCategory with id: " + subCategoryId + " not found!"));
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new NotFoundException("Category with id: " + request.categoryId() + " not found!"));
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
            SubCategory subCategory = repository.findById(subCategoryId)
                    .orElseThrow(() ->
                            new NotFoundException("SubCategory with id: " + subCategoryId + " not found!"));
            subCategory.setCategory(new Category());
            menuItemRepository.deleteAll(subCategory.getMenuItems());
            repository.deleteById(subCategoryId);
            return new SimpleResponse("DELETE", "SubCategory with id: " + subCategoryId + " successfully deleted!");
        }
        return new SimpleResponse("FAIL","SubCategory with id: "+subCategoryId+" failed to delete!");
    }
}
