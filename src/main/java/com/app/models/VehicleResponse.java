package com.app.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VehicleResponse {
    private long id;
    private String mark;
    private String model;
    private String type;
    private int mileage;
    private int price;
    private long idProfile;
}
