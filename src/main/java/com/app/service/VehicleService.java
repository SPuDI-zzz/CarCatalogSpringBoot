package com.app.service;

import com.app.entity.Vehicle;
import com.app.exception.NotFoundException;
import com.app.mapping.VehicleMapper;
import com.app.models.VehicleRequest;
import com.app.models.VehicleResponse;
import com.app.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VehicleService {
    public static final String VEHICLE_WITH_ID_NOT_FOUND = "Vehicle with id '%s' not found";
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public List<VehicleResponse> getAll() {
        return vehicleMapper.mapToVehicleResponseList(vehicleRepository.findAll());
    }

    public VehicleResponse getById(Long id) {
        return vehicleMapper.mapToVehicleResponse(vehicleRepository.findVehicleById(id)
                .orElseThrow(() -> new NotFoundException(VEHICLE_WITH_ID_NOT_FOUND.formatted(id))));
    }

    public List<VehicleResponse> getByIdProfile(Long idProfile) {
        return vehicleMapper.mapToVehicleResponseList(vehicleRepository.findVehiclesByIdProfile(idProfile));
    }

    public VehicleResponse insert(VehicleRequest vehicleRequest) {
        Vehicle vehicle = vehicleRepository.save(vehicleMapper.mapToVehicle(vehicleRequest));
        return vehicleMapper.mapToVehicleResponse(vehicle);
    }

    public VehicleResponse update(Long id, VehicleRequest vehicleRequest) {
        if (!vehicleRepository.existsById(id)) {
            throw new NotFoundException(VEHICLE_WITH_ID_NOT_FOUND.formatted(id));
        }

        Vehicle vehicle = vehicleRepository.save(vehicleMapper.mapToVehicle(id, vehicleRequest));
        return vehicleMapper.mapToVehicleResponse(vehicle);
    }

    public void delete(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new NotFoundException(VEHICLE_WITH_ID_NOT_FOUND.formatted(id));
        }

        vehicleRepository.deleteById(id);
    }
}
