package rochi.burko.Meli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rochi.burko.Meli.model.Warehouse;


public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Warehouse findOneByISOAndNumber(String ISOCode, int number);
}
