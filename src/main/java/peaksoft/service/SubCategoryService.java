package peaksoft.service;

import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entity.SubCategory;

import java.util.List;

public interface SubCategoryService{
    List<SubCategory> getAll(Long restaurantId);
    SimpleResponse save(Long restaurantId, SubCategoryRequest request);
    SubCategory finById(Long restaurantId,Long subCategoryId);
    SimpleResponse update(Long restaurantId,Long subCategoryId, SubCategoryRequest request);
    SimpleResponse delete(Long restaurantId,Long subCategoryId);
}
