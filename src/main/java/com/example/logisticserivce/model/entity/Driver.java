package com.example.logisticserivce.model.entity;

import com.example.logisticserivce.model.enumerator.DriverStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name= "Driver")
public class Driver implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;

    @NotBlank(message = "'name' must not be blank")
    @Column(name = "Name", length = 64, nullable = false)
    private String name;

    @NotBlank(message = "'surname' must not be blank")
    @Column(name = "Surname", length = 64, nullable = false)
    private String surname;

    @OneToOne
    @JoinColumn(name = "LorryId", referencedColumnName = "Id")
    private Lorry lorry;

    @NotBlank(message = "'phoneNumber' must not be blank")
    @Column(name = "PhoneNumber", length = 64, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", length = 64, nullable = false)
    private DriverStatus status;

    @NotBlank(message = "'login' must not be blank")
    @Column(name = "Login", length = 64, nullable = false, unique = true)
    private String login;

    @JsonIgnore
    @NotBlank(message = "'password' must not be blank")
    @Column(name = "Password", length = 64, nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(targetEntity = Job.class, mappedBy = "driver")
    private List<Job> jobList;
}
