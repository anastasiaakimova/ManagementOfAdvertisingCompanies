package by.akimova.ManagementOfAdvertisingCompanies.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

/**
 * The Campaign is a model of db's campaign
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

@Data
@Entity
@Table(name = "campaign")
public class Campaign {
    @Id
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "link")
    private String link;
    @Column(name = "age")
    private String age;
    @Column(name = "country")
    private String country;
    @Column(name = "language")
    private String language;
    @Column(name = "geolocation")
    private String geolocation;
    @Column(name = "isactive")
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "advertiser")
    private Advertiser advertiser;
}
