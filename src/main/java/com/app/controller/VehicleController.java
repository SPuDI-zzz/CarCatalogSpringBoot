package com.app.controller;

import com.app.models.VehicleRequest;
import com.app.models.VehicleResponse;
import com.app.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static com.app.constants.ValidationConstants.ID_POSITIVE_MESSAGE;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping("/vehicles")
    public List<VehicleResponse> getAll() {
        return vehicleService.getAll();
    }

    @GetMapping("/vehicles/{id}")
    public VehicleResponse getById(@PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long id) {
        return vehicleService.getById(id);
    }

    @GetMapping("/profiles/{id}/vehicles")
    public List<VehicleResponse> getByProfileId(@PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long id) {
        return vehicleService.getByIdProfile(id);
    }

    @PostMapping("/vehicles")
    public VehicleResponse insert(@Valid @RequestBody VehicleRequest vehicleRequest) {
        return vehicleService.insert(vehicleRequest);
    }

    @PutMapping("/vehicles/{id}")
    public VehicleResponse update(
            @PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long id,
            @Valid @RequestBody VehicleRequest vehicleRequest) {
        return vehicleService.update(id, vehicleRequest);
    }

    @DeleteMapping("/vehicles/{id}")
    public void delete(@PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long id) {
        vehicleService.delete(id);
    }
}
