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

    private String name;

    private String surname;

    private String login;

    private String password;
}
