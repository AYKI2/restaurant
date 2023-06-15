package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.PriceResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.exceptions.BadRequestException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<ChequeResponse> getAll(Long restaurantId) {
        List<Cheque> cheques = repository.getAll(restaurantId);
        List<ChequeResponse> chequeResponses = new ArrayList<>();
        for (Cheque cheque: cheques) {
            ChequeResponse chequeResponse = new ChequeResponse(
                    cheque.getEmployee().getFirstName() + " " + cheque.getEmployee().getLastName(),
                    cheque.getMenuItems().stream().map(MenuItem::getName).collect(Collectors.toList()),
                    cheque.getCreatedAt(),
                    cheque.getPriceAverage(),
                    new BigDecimal(cheque.getEmployee().getRestaurant().getService()),
                    cheque.getPriceAverage()
                            .multiply(new BigDecimal(cheque.getEmployee().getRestaurant().getService()))
                            .divide(new BigDecimal(100))
                            .add(cheque.getPriceAverage())
            );
            chequeResponses.add(chequeResponse);
        }
        return chequeResponses;
    }

    @Override
    public String getAllChequesByUser(Long userId) {
        LocalDate today = LocalDate.now();
        List<Cheque> cheques = repository.findAll();
        double total = 0D;
        for (Cheque cheque:cheques) {
            if(cheque.getEmployee().getId().equals(userId)){
                if(cheque.getCreatedAt().equals(today)) {
                    total += cheque.getPriceAverage().doubleValue();
                }
            }
        }
        return "Total cost of user with id: "+userId+" = "+total;
    }
    @Override
    public String getAveragePrice(Long restaurantId) {
        PriceResponse averagePrice = repository.getAveragePrice(restaurantId);
        BigDecimal average = BigDecimal.valueOf(averagePrice.priceAverage().doubleValue() + (averagePrice.priceAverage().doubleValue() * averagePrice.service() / 100));
        return "Restaurant average bill: " + average;
    }

    @Override
    public SimpleResponse save(Long restaurantId, ChequeRequest request) {
        Cheque cheque = new Cheque();
        User user = userRepository.findById(request.userId())
                .orElseThrow(() ->
                        new NotFoundException("User with id: " + request.userId() + " not found!"));
        List<MenuItem> menuItems = new ArrayList<>();
        double sum = 0;
        for (String name:request.menuItems()) {
            MenuItem menuItem = menuItemRepository.findByName(name)
                    .orElseThrow(()->
                            new NotFoundException("MenuItem with name: "+name+" not found!"));
            if(menuItem.getStopList().getDate().equals(LocalDate.now())){
                throw new BadRequestException("The "+ menuItem.getName()+" is currently out of stock. Because of ("+menuItem.getStopList().getReason()+")");
            }
            sum += menuItem.getPrice().doubleValue();
            menuItems.add(menuItem);
        }
        cheque.setEmployee(user);
        cheque.setCreatedAt(LocalDate.now());
        cheque.setPriceAverage(BigDecimal.valueOf(sum));
        cheque.setMenuItems(menuItems);
        repository.save(cheque);
        return new SimpleResponse("SAVE","Cheque successfully saved!");
    }

    @Override
    public ChequeResponse finById(Long chequeId) {
        Cheque cheque = repository.findById(chequeId)
                .orElseThrow(() ->
                        new NotFoundException("Cheque with id: " + chequeId + " not found!"));
        Restaurant restaurant = restaurantRepository.findById(cheque.getEmployee().getRestaurant().getId())
                .orElseThrow(() ->
                        new NotFoundException("Restaurant with id: " + cheque.getEmployee().getRestaurant().getId() + " not found!"));
        BigDecimal total =  cheque.getPriceAverage()
                .multiply(new BigDecimal(cheque.getEmployee().getRestaurant().getService()))
                .divide(new BigDecimal(100))
                .add(cheque.getPriceAverage());

        String fullName = cheque.getEmployee().getFirstName()+" "+cheque.getEmployee().getLastName();
        List<MenuItem> menuItems = cheque.getMenuItems();
        List<String> responses = new ArrayList<>();
        for (MenuItem menuItem: menuItems) {
            MenuItemResponse item = new MenuItemResponse(
                    menuItem.getId(),
                    menuItem.getName(),
                    menuItem.getImage(),
                    menuItem.getPrice(),
                    menuItem.getDescription(),
                    menuItem.isVegetarian(),
                    menuItem.getSubCategory().getName()
            );
            responses.add(item.name());
        }

        return new ChequeResponse(
                fullName,
                responses,
                cheque.getCreatedAt(),
                cheque.getPriceAverage(),
                new BigDecimal(restaurant.getService()),
                total
        );
    }

    @Override
    public SimpleResponse update(Long restaurantId, Long chequeId, ChequeRequest request) {
        if(!repository.existsById(chequeId)){
            return new SimpleResponse("FAIL", "Cheque failed to update!");
        }
        Cheque cheque = repository.findById(chequeId)
                .orElseThrow(() ->
                        new NotFoundException("Cheque with id: " + chequeId + " not found!"));
        User user = userRepository.findById(request.userId())
                .orElseThrow(() ->
                        new NotFoundException("User with id: " + request.userId() + " not found!"));
        List<MenuItem> menuItems = new ArrayList<>();
        double sum = 0;
        for (String name:request.menuItems()) {
            MenuItem menuItem = menuItemRepository.findByName(name)
                    .orElseThrow(()->
                            new NotFoundException("MenuItem with name: "+name+" not found!"));
            if(menuItem.getStopList().getDate().equals(LocalDate.now())){
                throw new BadRequestException("The "+ menuItem.getName()+" is currently out of stock. Because of ("+menuItem.getStopList().getReason()+")");
            }
            sum += menuItem.getPrice().doubleValue();
            menuItems.add(menuItem);
        }
        cheque.setEmployee(user);
        cheque.setMenuItems(menuItems);
        cheque.setPriceAverage(BigDecimal.valueOf(sum));
        return new SimpleResponse("UPDATE","Cheque with id: "+chequeId+" successfully updated!");
    }

    @Override
    public SimpleResponse delete(Long restaurantId, Long chequeId) {
        if(!repository.existsById(chequeId)){
            return new SimpleResponse("FAIL", "Cheque failed to delete!");
        }
        repository.deleteById(chequeId);
        return new SimpleResponse("DELETE","Cheque with id: "+chequeId+" successfully deleted!");
    }
}

