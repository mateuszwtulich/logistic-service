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

    @NotBlank(message = "'commissionedParty' must not be blank")
    private String commissionedParty;

    @NonNull
    private Long principalId;

    @NonNull
    private Long managerId;

    @NonNull
    private Long driverId;

    @NonNull
    private Long cargoId;

    @NonNull
    private Long unloadingId;

    @NonNull
    private Long loadingId;

    @NotBlank(message = "'placeOfIssue' must not be blank")
    private String placeOfIssue;

    @NonNull
    private Double weight;

    @NonNull
    private Integer payRate;

    @NotBlank(message = "'comment' must not be blank")
    private String comment;

    @NotBlank(message = "'status' must not be blank")
    private JobStatus status;
}
