package peaksoft.service;

import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService{
    List<SubCategoryResponse> getAll(Long restaurantId);
    List<SubCategoryResponse> getAllByCategory(Long categoryId, String ascOrDesc);
    SimpleResponse save(Long restaurantId, SubCategoryRequest request);
    SubCategoryResponse finById(Long restaurantId,Long subCategoryId);
    SimpleResponse update(Long restaurantId,Long subCategoryId, SubCategoryRequest request);
    SimpleResponse delete(Long restaurantId,Long subCategoryId);
}
