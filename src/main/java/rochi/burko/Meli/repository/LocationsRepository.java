package rochi.burko.Meli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rochi.burko.Meli.model.Location;


public interface LocationsRepository extends JpaRepository<Location, Long> {

}
