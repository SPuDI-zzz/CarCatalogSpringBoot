package com.app.mapping;

import com.app.entity.Vehicle;
import com.app.models.VehicleRequest;
import com.app.models.VehicleResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehicleMapper {
    public VehicleResponse mapToVehicleResponse(Vehicle vehicle) {
        return VehicleResponse
                .builder()
                .id(vehicle.getId())
                .mark(vehicle.getMark())
                .model(vehicle.getModel())
                .type(vehicle.getType())
                .price(vehicle.getPrice())
                .mileage(vehicle.getMileage())
                .idProfile(vehicle.getIdProfile())
                .build();
    }

    public List<VehicleResponse> mapToVehicleResponseList(List<Vehicle> vehicles) {
        return vehicles
                .stream()
                .map(this::mapToVehicleResponse)
                .collect(Collectors.toList());
    }

    public Vehicle mapToVehicle(VehicleRequest vehicleRequest) {
        return Vehicle
                .builder()
                .mark(vehicleRequest.getMark())
                .model(vehicleRequest.getModel())
                .type(vehicleRequest.getType())
                .price(vehicleRequest.getPrice())
                .mileage(vehicleRequest.getMileage())
                .idProfile(vehicleRequest.getIdProfile())
                .build();
    }

    public Vehicle mapToVehicle(Long id, VehicleRequest vehicleRequest) {
        return Vehicle
                .builder()
                .id(id)
                .mark(vehicleRequest.getMark())
                .model(vehicleRequest.getModel())
                .type(vehicleRequest.getType())
                .price(vehicleRequest.getPrice())
                .mileage(vehicleRequest.getMileage())
                .idProfile(vehicleRequest.getIdProfile())
                .build();
    }
}
