package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class StopListServiceImpl implements StopListService {
    private final StopListRepository repository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<StopList> getAll() {
        return repository.findAll();
    }

    @Override
    public SimpleResponse save(StopListRequest request) {
        MenuItem menuItem = menuItemRepository.findById(request.menuItemId())
                .orElseThrow(()->
                        new NoSuchElementException("MenuItem with id: "+request.menuItemId()+" not found!"));
        StopList existingStopList = repository.findByDate(request.date());

        if (existingStopList != null) {
            throw new RuntimeException("StopList already exists for this MenuItem and date!");
        }

        StopList stopList = new StopList();
        stopList.setReason(request.reason());
        stopList.setDate(request.date());
        menuItem.setStopList(stopList);
        repository.save(stopList);
        return new SimpleResponse("SAVE", "StopList successfully saved!");
    }

    @Override
    public StopList finById(Long stopListId) {
        return repository.findById(stopListId)
                .orElseThrow(()->
                        new NoSuchElementException("StopList with id: "+ stopListId+" not found!"));
    }

    @Override
    public SimpleResponse update(Long stopListId, StopListRequest request) {
        MenuItem menuItem = menuItemRepository.findById(request.menuItemId())
                .orElseThrow(()->
                        new NoSuchElementException("MenuItem with id: "+request.menuItemId()+" not found!"));
        StopList existingStopList = repository.findByDate(request.date());

        if (existingStopList != null) {
            throw new RuntimeException("StopList already exists for this MenuItem and date!");
        }

        StopList stopList = repository.findById(stopListId)
                .orElseThrow(() ->
                        new NoSuchElementException("StopList with id: " + stopListId + " not found!"));
        if(repository.existsById(stopListId)) {
            stopList.setReason(request.reason());
            stopList.setDate(request.date());
            menuItem.setStopList(stopList);
            repository.save(stopList);
            return new SimpleResponse("UPDATE", "StopList with id: " + stopListId + " successfully updated!");
        }
        return new SimpleResponse("FAIL", "StopList with id: " + stopListId + " failed to update!");
    }

    @Override
    public SimpleResponse delete(Long stopListId) {
        if(repository.existsById(stopListId)){
            repository.deleteById(stopListId);
            return new SimpleResponse("DELETE", "StopList with id: " + stopListId + " successfully deleted!");
        }
        return new SimpleResponse("FAIL","StopList with id: "+stopListId+" failed to delete!");
    }
}
