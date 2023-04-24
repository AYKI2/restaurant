package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryApi {
    private final CategoryService service;

    @Autowired
    public CategoryApi(CategoryService service) {
        this.service = service;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<CategoryResponse> getAll(){
        return service.getAll();
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse save(@RequestBody CategoryRequest request){
        return service.save(request);
    }

    @GetMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public CategoryResponse finById(@PathVariable Long categoryId){
        return service.finById(categoryId);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse update(@PathVariable Long categoryId,
                                 @RequestBody CategoryRequest request){
        return service.update(categoryId,request);
    }
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse delete(@PathVariable Long categoryId){
        return service.delete(categoryId);
    }
}
