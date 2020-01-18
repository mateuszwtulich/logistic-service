package com.example.logisticserivce.model.dto;

import com.example.logisticserivce.model.enumerator.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private String name;

    private String surname;

    private Long lorryId;

    private String phoneNumber;

    private DriverStatus status;

    private String login;

    private String password;
}
