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
@Embeddable
public class LocalizationId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "Lorry", referencedColumnName = "LicenceNumber", nullable = false, unique = true)
    private Lorry lorry;

    @NonNull
    private Timestamp date;
}
