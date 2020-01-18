package com.example.logisticserivce.model.dto;

import com.example.logisticserivce.model.enumerator.LorryStatus;
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
    private String licenceNumber;

    private String model;

    private LorryStatus status;
}
