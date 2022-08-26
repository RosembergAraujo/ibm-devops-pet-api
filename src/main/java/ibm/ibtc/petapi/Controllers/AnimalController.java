package ibm.ibtc.petapi.Controllers;

import ibm.ibtc.petapi.Models.AnimalModel;
import ibm.ibtc.petapi.Models.CustomerModel;
import ibm.ibtc.petapi.Repositories.AnimalRepository;
import ibm.ibtc.petapi.Repositories.CustomerRepository;
import ibm.ibtc.petapi.ViewModels.AnimalCreateViewModel;
import ibm.ibtc.petapi.ViewModels.CustomerCreateViewModel;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    private final AnimalRepository _animalRepository;

    public AnimalController(,
            AnimalRepository animalRepository) {
        this._animalRepository = animalRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.CREATED).body(new Object() {
            public final Object animals = _animalRepository.findAll();
        });
    }

    @PostMapping("/findById/{param_id}")
    public ResponseEntity<Object> findById(@PathVariable int param_id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Object() {
            public final Object animal = _animalRepository.findById(param_id);
        });
    }

    @DeleteMapping("/deleteById/{param_id}")
    public ResponseEntity<Object> deleteById(@PathVariable int param_id) {

        Optional<AnimalModel> animal = _animalRepository.findById(param_id);

        if (animal.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Object() {
                public final Object message = "Animal not found!";
            });
        } else {
            _animalRepository.deleteById(param_id);


            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Object() {
                public final Object message = "Animal Deleted!";
            });
        }
    }

    @PostMapping("/createAnimal")
    public ResponseEntity<Object> createAnimal(@RequestBody AnimalCreateViewModel animal) {

        AnimalModel animalModel = new AnimalModel();
        BeanUtils.copyProperties(animal, animalModel);

        animalModel.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Object() {
            public final Object animal = _animalRepository.save(animalModel);
        });
    }


    @PutMapping("/updateCustomer/{param_id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable int param_id, @RequestBody Map<String, Object> req) {

        Optional<AnimalModel> animalModel = _animalRepository.findById(param_id);

        if (animalModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Object() {
                public final Object message = "Animal not found!";
            });
        } else {
            AnimalModel animal = new AnimalModel();
            BeanUtils.copyProperties(animalModel.get(), animal);

            if (req.get("name") != null) animal.setName((String) req.get("name"));
            if (req.get("age") != null) animal.setAge((Integer) req.get("age"));
            if (req.get("weight") != null) animal.setWeight((Double) req.get("weight"));

            animal.setUpdatedAt(LocalDateTime.now());

            _animalRepository.save(animal);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Object() {
                public final Object updatedAnimal = animal;
            });
        }

    }

}
