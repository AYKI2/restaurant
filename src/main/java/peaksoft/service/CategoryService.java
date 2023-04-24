package peaksoft.service;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();
    SimpleResponse save(CategoryRequest request);
    CategoryResponse finById(Long categoryId);
    SimpleResponse update(Long categoryId, CategoryRequest request);
    SimpleResponse delete(Long categoryId);
}
