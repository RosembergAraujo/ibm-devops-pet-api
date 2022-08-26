package ibm.ibtc.petapi.Repositories;



import ibm.ibtc.petapi.Models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerModel, Integer> {
    public Object findByCustomerId(Integer customerId);
}
