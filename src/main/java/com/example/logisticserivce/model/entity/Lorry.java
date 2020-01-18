package com.example.logisticserivce.model.entity;

import com.example.logisticserivce.model.enumerator.LorryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "Lorry")
public class Lorry implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;

    @NotBlank(message = "'licenceNumber' must not be blank")
    @Column(name = "LicenceNumber", length = 8, nullable = false, unique = true)
    private String licenceNumber;

    @NotBlank(message = "'model' must not be blank")
    @Column(name = "Model", length = 64, nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 64, nullable = false)
    private LorryStatus status;

//    @JsonIgnore
//    @ManyToMany(targetEntity = Localization.class, mappedBy = "lorry", cascade = CascadeType.ALL)
//    private List<Localization> localizationList;

    @JsonIgnore
    @OneToOne(targetEntity = Driver.class, mappedBy = "lorry")
    private Driver driver;
}
