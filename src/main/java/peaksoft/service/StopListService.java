package peaksoft.service;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;

import java.util.List;

public interface StopListService {
    List<StopListResponse> getAll();
    SimpleResponse save(StopListRequest request);
    StopListResponse finById(Long stopListId);
    SimpleResponse update(Long stopListId, StopListRequest request);
    SimpleResponse delete(Long stopListId);
}
