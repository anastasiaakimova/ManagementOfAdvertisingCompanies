package by.akimova.ManagementOfAdvertisingCompanies.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

/**
 * The User is a model of db's user
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Data
@Entity
@Table(name = "usr")
public class User {
    @Id
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "mail")
    private String mail;
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "isactive")
    private Boolean isActive;


}
