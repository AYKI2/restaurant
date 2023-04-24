package peaksoft.service;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface ChequeService {
    List<ChequeResponse> getAll(Long restaurantId);
    String getAllChequesByUser(Long userId);
    String getAveragePrice(Long restaurantId);
    SimpleResponse save(Long restaurantId, ChequeRequest request);
    ChequeResponse finById(Long chequeId);
    SimpleResponse update(Long restaurantId,Long chequeId, ChequeRequest request);
    SimpleResponse delete(Long restaurantId,Long chequeId);

}
