package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Category;
import peaksoft.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/{restaurantId}/categories")
public class CategoryApi {
    private final CategoryService service;

    @Autowired
    public CategoryApi(CategoryService service) {
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<Category> getAll(@PathVariable Long restaurantId){
        return service.getAll();
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse save(@RequestBody CategoryRequest request, @PathVariable Long restaurantId){
        return service.save(request);
    }

    @GetMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public Category finById(@PathVariable Long categoryId, @PathVariable Long restaurantId){
        return service.finById(restaurantId,categoryId);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse update(@PathVariable Long categoryId,
                                 @RequestBody CategoryRequest request, @PathVariable Long restaurantId){
        return service.update(restaurantId,categoryId,request);
    }
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse delete(@PathVariable Long categoryId, @PathVariable Long restaurantId){
        return service.delete(restaurantId,categoryId);
    }
}
