package com.example.logisticserivce.model.entity;

import com.example.logisticserivce.model.enumerator.JobStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "Job")
public class Job implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;

    @NonNull
    @Column(name = "Number", nullable = false, unique = true)
    private Long number;

    @NonNull
    @Column(name = "Date", nullable = false)
    private Timestamp date;

    @NotBlank(message = "'commissionedParty' must not be blank")
    @Column(name = "CommissionedParty", length = 64, nullable = false)
    private String commissionedParty;

    @ManyToOne
//    @Column(name = "PrincipalId")
    @JoinColumn(name = "Principal", referencedColumnName = "Id", nullable = false)
    private Principal principal;

    @ManyToOne
//    @Column(name = "ManagerId")
    @JoinColumn(name = "Manager", referencedColumnName = "Id", nullable = false)
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "Driver", referencedColumnName = "Id", nullable = true)
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "Cargo", referencedColumnName = "Id", nullable = false)
    private Cargo cargo;

    @ManyToOne
//    @Column(name = "LoadingId")
    @JoinColumn(name = "Loading", referencedColumnName = "Id", nullable = false)
    private Loading loading;

    @ManyToOne
//    @Column(name = "UnloadingId")
    @JoinColumn(name = "Unloading", referencedColumnName = "Id", nullable = false)
    private Unloading unloading;

    @NotBlank(message = "'placeOfIssue' must not be blank")
    @Column(name = "PlaceOfIssue", nullable = false)
    private String placeOfIssue;

    @NonNull
    @Column(name = "Weight", nullable = false)
    private Double weight;

    @NonNull
    @Column(name = "PayRate", nullable = false)
    private Integer payRate;

    @NotBlank(message = "'comment' must not be blank")
    @Column(name = "Comment")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private JobStatus status;
}