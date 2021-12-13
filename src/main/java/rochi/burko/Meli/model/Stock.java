package rochi.burko.Meli.model;

import javax.persistence.*;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "product")
    private String product;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name="location_id", nullable=false)
    private Location location;

    public Stock() {
    }

    public Stock(String product, int quantity, Location location) {
        this.product = product;
        this.quantity = quantity;
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Stock [location=" + location + ", product=" + product + ", quantity=" + quantity + "]";
    }
}