package ibm.ibtc.petapi.Models;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "service_order_t")
public class ServiceOrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceOrderId;

    private Double valor;
    private LocalDateTime entryAt;
    private LocalDateTime exitAt;

    @OneToOne
    @JoinColumn(name = "customerid")
    private CustomerModel customer;
}