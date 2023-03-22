package peaksoft.service;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    SimpleResponse save(CategoryRequest request);
    Category finById(Long restaurantId,Long categoryId);
    SimpleResponse update(Long restaurantId,Long categoryId, CategoryRequest request);
    SimpleResponse delete(Long restaurantId,Long categoryId);
}
