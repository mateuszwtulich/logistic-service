package com.example.logisticserivce.model.dto;

import com.example.logisticserivce.model.enumerator.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    @NotBlank(message = "'name' must not be blank")
    private String name;

    @NotBlank(message = "'surname' must not be blank")
    private String surname;

    private LorryDto lorry;

    @NotBlank(message = "'phoneNumber' must not be blank")
    private String phoneNumber;

    @NotBlank(message = "'status' must not be blank")
    private DriverStatus status;


}
