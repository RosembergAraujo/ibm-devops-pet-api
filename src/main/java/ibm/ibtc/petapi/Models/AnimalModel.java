package ibm.ibtc.petapi.Models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "animals_t")
public class AnimalModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer animalId;

    private String name;
    private Integer age;
    private Double weight;

    @OneToOne
    @JoinColumn (name = "customerid")
    private CustomerModel customer;
}
