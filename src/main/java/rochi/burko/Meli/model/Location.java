package rochi.burko.Meli.model;


import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "area")
    private String area;

    @Column(name = "hall")
    private int hall;

    @Column(name = "location_row")
    private int locationRow;

    @Column(name = "side")
    private String side;

    @OneToMany(mappedBy="location")
    private List<Stock> stock;

    @ManyToOne
    @JoinColumn(name="warehouse_id", nullable=false)
    private Warehouse warehouse;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getHall() {
        return hall;
    }

    public void setHall(int hall) {
        this.hall = hall;
    }

    public int getLocationRow() {
        return locationRow;
    }

    public void setLocationRow(int locationRow) {
        this.locationRow = locationRow;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public List<Stock> getStock() {
        return stock;
    }

    public void setStock(List<Stock> stock) {
        this.stock = stock;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public String toString() {

        DecimalFormat formatter = new DecimalFormat("00");

        StringBuilder builder = new StringBuilder();
        builder.append(area);
        builder.append("-");
        builder.append(formatter.format(hall));
        builder.append("-");
        builder.append(formatter.format(locationRow));
        builder.append("-");
        builder.append(side);

        return builder.toString();
    }

}
