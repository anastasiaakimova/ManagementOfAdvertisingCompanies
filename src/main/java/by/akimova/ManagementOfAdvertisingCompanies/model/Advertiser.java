package by.akimova.ManagementOfAdvertisingCompanies.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

/**
 * The Advertiser is a model of db's advertiser
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

@Data
@Entity
@Table(name = "advertiser")
public class Advertiser {
    @Id
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "isactive")
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "usr")
    private User usr;
}
