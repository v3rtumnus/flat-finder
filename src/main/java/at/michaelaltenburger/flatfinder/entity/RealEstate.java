package at.michaelaltenburger.flatfinder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "REAL_ESTATE")
public class RealEstate {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SQUARE_METRES")
    private Double squareMetres;

    @Column(name = "ROOMS")
    private Double rooms;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private RealEstateType type;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private RealEstateState state;

    @Column(name = "WEBSITE")
    @Enumerated(EnumType.STRING)
    private Website website;

    @Column(name = "CITY")
    private String city;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "PURCHASE_TYPE")
    @Enumerated(EnumType.STRING)
    private PurchaseType purchaseType;

    @Column(name = "URL")
    private String url;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "PROJECT")
    private boolean project;

    public RealEstate() {

    }

    public RealEstate(String id, String title, RealEstateType type, PurchaseType purchaseType,
                      Website website, String city, boolean project) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.purchaseType = purchaseType;
        this.website = website;
        this.city = city;
        this.project = project;

        this.state = RealEstateState.SAVED;
    }
}
