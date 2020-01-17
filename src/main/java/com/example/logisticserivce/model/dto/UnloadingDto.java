package com.example.logisticserivce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnloadingDto {
    @NonNull
    private Double latitude;

    @NonNull
    private Double longitude;

    @NotBlank(message = "'address' must not be blank")
    private String address;
}
