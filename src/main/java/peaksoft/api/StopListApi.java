package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.service.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/stopLists")
public class StopListApi {
    private final StopListService service;
    @Autowired
    public StopListApi(StopListService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<StopListResponse> getAll(){
        return service.getAll();
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse save(@RequestBody StopListRequest request){
        return service.save(request);
    }

    @GetMapping("/{stopList}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public StopListResponse finById(@PathVariable Long stopList){
        return service.finById(stopList);
    }

    @PutMapping("/{stopList}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse update(@PathVariable Long stopList,
                                 @RequestBody StopListRequest request){
        return service.update(stopList,request);
    }
    @DeleteMapping("/{stopList}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long stopList){
        return service.delete(stopList);
    }
}
