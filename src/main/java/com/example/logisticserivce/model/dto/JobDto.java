package com.example.logisticserivce.model.dto;

import com.example.logisticserivce.model.enumerator.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {

    @NonNull
    private Long number;

    @NonNull
    private Timestamp date;

    private String commissionedParty;

    @NonNull
    private Long principalId;

    @NonNull
    private Long managerId;

    private Long driverId;

    @NonNull
    private Long cargoId;

    @NonNull
    private Long unloadingId;

    @NonNull
    private Long loadingId;

    private String placeOfIssue;

    @NonNull
    private Double weight;

    @NonNull
    private Integer payRate;

    private String comment;

    private JobStatus status;
}
