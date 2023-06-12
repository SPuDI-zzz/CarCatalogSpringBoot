package com.app.controller;

import com.app.models.ProfileRequest;
import com.app.models.ProfileResponse;
import com.app.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

import static com.app.constants.ValidationConstants.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping
    public List<ProfileResponse> getAll() {
        return profileService.getAll();
    }

    @GetMapping("/{login}")
    public ProfileResponse getByLogin(@PathVariable @Size(min = LOGIN_MIN_SIZE, max = LOGIN_MAX_SIZE, message = LOGIN_SIZE_MESSAGE) String login) {
        return profileService.getProfileByLogin(login);
    }

    @GetMapping("/{id}")
    public ProfileResponse getById(@PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long id) {
        return profileService.getProfileById(id);
    }

    @PostMapping
    public ProfileResponse insert(@Valid @RequestBody ProfileRequest profileRequest) {
        return profileService.insert(profileRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long id) {
        profileService.delete(id);
    }

    @PutMapping("/{id}")
    public ProfileResponse update(
            @PathVariable @Positive(message = ID_POSITIVE_MESSAGE) Long id,
            @Valid @RequestBody ProfileRequest profileRequest) {
        return profileService.update(id, profileRequest);
    }
}
