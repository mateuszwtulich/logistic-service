package com.example.logisticserivce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LorryDto {
    @NotBlank(message = "'licenceNumber' must not be blank")
    private String licenceNumber;

    @NotBlank(message = "'model' must not be blank")
    private String model;

    @NotBlank(message = "'status' must not be blank")
    private String status;
}
