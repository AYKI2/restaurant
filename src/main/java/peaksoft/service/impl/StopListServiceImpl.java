package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exceptions.AlreadyExistsException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StopListServiceImpl implements StopListService {
    private final StopListRepository repository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<StopListResponse> getAll() {
        List<StopList> lists = repository.findAll();
        List<StopListResponse> responses = new ArrayList<>();
        for (StopList list:lists) {
            if (!list.getDate().equals(LocalDate.now())) {
                MenuItem menuItem = menuItemRepository.findByStopList(list)
                        .orElseThrow(() ->
                                new NotFoundException("MenuItem not found!"));
                responses.add(
                        new StopListResponse(
                                list.getId(),
                                list.getReason(),
                                list.getDate(),
                                menuItem.getName()
                        )
                );
            }
        }
        return responses;
    }

    @Override
    public SimpleResponse save(StopListRequest request) {
        MenuItem menuItem = menuItemRepository.findById(request.menuItemId())
                .orElseThrow(()->
                        new NotFoundException("MenuItem with id: "+request.menuItemId()+" not found!"));
        if(menuItem.getStopList() != null) {
            throw new AlreadyExistsException("This MenuItem already has a StopList!");
        }
        checkDate(request);

        StopList stopList = new StopList();
        stopList.setReason(request.reason());
        stopList.setDate(request.date());
        menuItem.setStopList(stopList);
        repository.save(stopList);
        return new SimpleResponse("SAVE", "StopList successfully saved!");
    }

    @Override
    public StopListResponse finById(Long stopListId) {
        StopList stopList = repository.findById(stopListId)
                .orElseThrow(() ->
                        new NotFoundException("StopList with id: " + stopListId + " not found!"));
        MenuItem menuItem = menuItemRepository.findByStopList(stopList)
                .orElseThrow(() ->
                        new NotFoundException("MenuItem not found!"));
        return new StopListResponse(stopList.getId(),stopList.getReason(),stopList.getDate(),menuItem.getName());
    }

    @Override
    public SimpleResponse update(Long stopListId, StopListRequest request) {
        checkDate(request);
        StopList stopList = repository.findById(stopListId)
                .orElseThrow(() ->
                        new NotFoundException("StopList with id: " + stopListId + " not found!"));
        if(repository.existsById(stopListId)) {
            stopList.setReason(request.reason());
            stopList.setDate(request.date());
            repository.save(stopList);
            return new SimpleResponse("UPDATE", "StopList with id: " + stopListId + " successfully updated!");
        }
        return new SimpleResponse("FAIL", "StopList with id: " + stopListId + " failed to update!");
    }

    private void checkDate(StopListRequest request) {
        List<StopList> lists = repository.findAll();
        for (StopList list : lists) {
            MenuItem item = menuItemRepository.findByStopListId(list.getId())
                    .orElseThrow(() ->
                            new NotFoundException("MenuItem with id: " + request.menuItemId() + " not found!"));
            if (request.menuItemId().equals(item.getId()) && request.date().equals(list.getDate())) {
                throw new AlreadyExistsException("StopList already exists for this MenuItem and date!");
            }
        }
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
