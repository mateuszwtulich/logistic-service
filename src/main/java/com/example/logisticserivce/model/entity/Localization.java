package com.example.logisticserivce.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name= "Localization")
public class Localization implements Serializable {
    private static final long serialVersionUID = 1L;

//    @EmbeddedId
//    private LocalizationId id;
    @Id
    @ManyToOne
    @JoinColumn(name = "Lorry", referencedColumnName = "LicenceNumber", nullable = false, unique = true)
    private Lorry lorry;

    @Id
    @NonNull
    private Timestamp date;

    @NonNull
    @Column(name = "Latitude", nullable = false)
    private Double latitude;

    @NonNull
    @Column(name = "Longitude", nullable = false)
    private Double longitude;
}
