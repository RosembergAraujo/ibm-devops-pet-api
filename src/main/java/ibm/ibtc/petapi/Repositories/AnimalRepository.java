package ibm.ibtc.petapi.Repositories;



import ibm.ibtc.petapi.Models.AnimalModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<AnimalModel, Integer> {

}
