package com.app.repository;

import com.app.entity.Vehicle;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
    @NonNull
    List<Vehicle> findAll();

    Optional<Vehicle> findVehicleById(Long id);

    List<Vehicle> findVehiclesByIdProfile(Long idProfile);
}
