package rochi.burko.Meli.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rochi.burko.Meli.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findByProduct(String product);

    List<Stock> findByLocation(String location_id);
}
