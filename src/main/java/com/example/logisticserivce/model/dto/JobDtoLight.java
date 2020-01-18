package com.example.logisticserivce.model.dto;

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
public class JobDtoLight {
    @NonNull
    private Long number;

    @NonNull
    private Timestamp date;

    @NonNull
    private PrincipalDto principal;

    @NonNull
    private DriverDto driver;

    @NonNull
    private DriverDto cargo;

    @NonNull
    private UnloadingDto unloading;

    @NonNull
    private LoadingDto loading;

    @NotBlank(message = "'status' must not be blank")
    private String status;
}
