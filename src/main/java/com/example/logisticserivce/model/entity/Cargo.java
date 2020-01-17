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
@Table(name= "Cargo")
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;

    @NotBlank(message = "'type' must not be blank")
    @Column(name = "Type", nullable = false, unique = true)
    private String type;

    @JsonIgnore
    @OneToMany(targetEntity = Job.class, mappedBy = "cargo")
    private List<Job> jobList;
}