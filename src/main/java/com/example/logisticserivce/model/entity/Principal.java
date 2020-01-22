package com.example.logisticserivce.model.entity;

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
@Table(name= "Principal")
public class Principal implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;

    @NotBlank(message = "'name' must not be blank")
    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @NotBlank(message = "'address' must not be blank")
    @Column(name = "Address", nullable = false, unique = true)
    private String address;

    @JsonIgnore
    @OneToMany(targetEntity = Job.class, mappedBy = "principal")
    private List<Job> jobList;

    @JsonIgnore
    @OneToMany(targetEntity = Loading.class, mappedBy = "principal")
    private List<Loading> loadingList;
}