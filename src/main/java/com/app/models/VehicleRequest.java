package com.app.models;

import com.app.validation.ProfileExists;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
public class VehicleRequest {
    @NotBlank(message = "Mark can't be blank")
    @Size(max = 50, message = "Mark must be shorter than 50 characters")
    private String mark;
    @NotBlank(message = "Model can't be blank")
    @Size(max = 50, message = "Model must be shorter than 50 characters")
    private String model;
    @NotBlank(message = "Type can't be blank")
    @Size(max = 50, message = "Type must be shorter than 50 characters")
    private String type;
    @PositiveOrZero(message = "Mileage must be positive number or equals zero")
    private int mileage;
    @Positive(message = "Price must be positive number")
    private int price;
    @NotNull(message = "idProfile can't be null")
    @Positive(message = "idProfile must be positive number")
    @ProfileExists(message = "Profile with idProfile not exists")
    private long idProfile;
}
