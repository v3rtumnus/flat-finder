package at.michaelaltenburger.flatfinder.entity;

import at.michaelaltenburger.flatfinder.util.StringListConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "SEARCH_CONFIGURATION")
public class SearchConfiguration {

    @Id
    private Long id;

    @Column(name = "MIN_ROOMS")
    protected String minRooms;

    @Column(name = "MIN_SQUARE_METRES")
    protected String minSquareMetres;

    @Column(name = "MAX_SALES_PRICE")
    protected String maxSalesPrice;

    @Column(name = "MAX_RENT")
    protected String maxRent;

    @Column(name = "CITIES")
    @Convert(converter = StringListConverter.class)
    protected List<String> cities;
}
