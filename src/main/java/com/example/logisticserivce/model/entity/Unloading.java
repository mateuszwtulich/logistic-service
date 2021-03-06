package com.example.logisticserivce.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name= "Unloading")
public class Unloading implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;

    @NonNull
    @Column(name = "Latitude", nullable = false)
    private Double latitude;

    @NonNull
    @Column(name = "Longitude", nullable = false)
    private Double longitude;

    @NotBlank(message = "'address' must not be blank")
    @Column(name = "Address", nullable = false, unique = true)
    private String address;

    @OneToMany(targetEntity = Job.class, mappedBy = "unloading")
    private List<Job> jobList;

    @ManyToOne
//    @Column(name = "PrincipalId")
    @JoinColumn(name = "Principal", referencedColumnName = "Id", nullable = false)
    private Principal principal;
}