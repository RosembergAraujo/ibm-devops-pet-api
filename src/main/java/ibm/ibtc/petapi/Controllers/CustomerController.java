package ibm.ibtc.petapi.Controllers;

import ibm.ibtc.petapi.Models.CustomerModel;
import ibm.ibtc.petapi.Repositories.CustomerRepository;
import ibm.ibtc.petapi.ViewModels.CustomerCreateViewModel;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerRepository _customerRepository;

    public CustomerController(
            CustomerRepository _customerRepository) {
        this._customerRepository = _customerRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Object() {
            public final Object customers = _customerRepository.findAll();
        });
    }

    @PostMapping("/findById/{param_id}")
    public ResponseEntity<Object> findById(@PathVariable int param_id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Object() {
            public final Object customer = _customerRepository.findById(param_id);
        });
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Object> deleteById(@RequestBody Map<String, Integer> req) {

        Optional<CustomerModel> customer = _customerRepository.findById(req.get("id"));

        if (customer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Object() {
                public final Object message = "Customer not found!";
            });
        } else {
            _customerRepository.deleteById(req.get("id"));


            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Object() {
                public final Object message = "Customer Deleted!";
            });
        }
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerCreateViewModel customer) {

        CustomerModel customerModel = new CustomerModel();
        BeanUtils.copyProperties(customer, customerModel);

        customerModel.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Object() {
            public final Object customer = _customerRepository.save(customerModel);
        });
    }

    @PutMapping("/updateCustomer/{param_id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable int param_id, @RequestBody Map<String, Object> req) {

        Optional<CustomerModel> customerModel = _customerRepository.findById(param_id);

        if (customerModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Object() {
                public final Object message = "Customer not found!";
            });
        } else {
            CustomerModel customer = new CustomerModel();
            BeanUtils.copyProperties(customerModel.get(), customer);

            if (req.get("name") != null) customer.setName((String) req.get("name"));
            if (req.get("email") != null) customer.setTel((String) req.get("tel"));

            _customerRepository.save(customer);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Object() {
                public final Object updatedCustomer = customer;
            });
        }

    }

}
