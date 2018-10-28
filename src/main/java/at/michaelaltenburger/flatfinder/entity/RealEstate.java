package at.michaelaltenburger.flatfinder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RealEstate {
    private String id;
    private String title;
    private Double squareMetres;
    private Double rooms;
    private RealEstateType type;
    private Website website;
    private String city;
    private String postalCode;
    private Double price;
    private PurchaseType purchaseType;
    private String url;
    private String imageUrl;
    private boolean project;

    public RealEstate(String id, String title, RealEstateType type, PurchaseType purchaseType,
                      Website website, String city, boolean project) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.purchaseType = purchaseType;
        this.website = website;
        this.city = city;
        this.project = project;
    }
}
