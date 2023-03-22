package peaksoft.service;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.StopList;

import java.util.List;

public interface StopListService {
    List<StopList> getAll();
    SimpleResponse save(StopListRequest request);
    StopList finById(Long stopListId);
    SimpleResponse update(Long stopListId, StopListRequest request);
    SimpleResponse delete(Long stopListId);
}
