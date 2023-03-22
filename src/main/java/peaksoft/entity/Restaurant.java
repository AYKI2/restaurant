package peaksoft.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter @Setter
@NoArgsConstructor
public class Restaurant {
    @Id
    @SequenceGenerator(name = "restaurant_id_gen", sequenceName = "restaurant_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "restaurant_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String location;
    private String restType;
    private int numberOfEmployees;
    private BigDecimal service;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "restaurant")
    private List<User> employees;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "restaurant")
    private List<MenuItem> menuItems;

    public Restaurant(String name, String location, String restType, int numberOfEmployees, BigDecimal service) {
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
    }
}
