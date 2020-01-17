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
public class ManagerDto {

    @NotBlank(message = "'name' must not be blank")
    private String name;

    @NotBlank(message = "'surname' must not be blank")
    private String surname;
}
