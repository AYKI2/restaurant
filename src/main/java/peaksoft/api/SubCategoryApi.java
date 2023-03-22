package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entity.SubCategory;
import peaksoft.service.SubCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/{restaurantId}/subCategories")
public class SubCategoryApi {
    private final SubCategoryService service;
    @Autowired
    public SubCategoryApi(SubCategoryService service) {
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<SubCategory> getAll(@PathVariable Long restaurantId){
        return service.getAll(restaurantId);
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse save(@PathVariable Long restaurantId, @RequestBody SubCategoryRequest request){
        return service.save(restaurantId,request);
    }

    @GetMapping("/{subCategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public SubCategory finById(@PathVariable Long restaurantId, @PathVariable Long subCategoryId){
        return service.finById(restaurantId, subCategoryId);
    }

    @PutMapping("/{subCategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse update(@PathVariable Long restaurantId, @PathVariable Long subCategoryId,
                                 @RequestBody SubCategoryRequest request){
        return service.update(restaurantId,subCategoryId,request);
    }
    @DeleteMapping("/{subCategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse delete(@PathVariable Long restaurantId, @PathVariable Long subCategoryId){
        return service.delete(restaurantId,subCategoryId);
    }
}
