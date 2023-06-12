package com.app.service;

import com.app.entity.Profile;
import com.app.exception.NotFoundException;
import com.app.exception.ProfileAlreadyExistsException;
import com.app.mapping.ProfileMapper;
import com.app.models.ProfileRequest;
import com.app.models.ProfileResponse;
import com.app.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileService {
    public static final String PROFILE_WITH_LOGIN_NOT_FOUND = "Profile with login '%s' not found";
    public static final String PROFILE_WITH_ID_NOT_FOUND = "Profile with id '%s' not found";
    private static final String PROFILE_WITH_LOGIN_ALREADY_EXISTS = "Profile with login '%s' already exists";

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final PasswordEncoder passwordEncoder;

    public List<ProfileResponse> getAll() {
        return profileMapper.mapToProfileResponseList(profileRepository.findAll());
    }

    public ProfileResponse getProfileByLogin(String login) {
        return profileMapper.mapToProfileResponse(profileRepository.findProfileByLogin(login)
                .orElseThrow(() -> new NotFoundException(PROFILE_WITH_LOGIN_NOT_FOUND.formatted(login))));
    }

    public ProfileResponse getProfileById(Long id) {
        return profileMapper.mapToProfileResponse(profileRepository.findProfileById(id)
                .orElseThrow(() -> new NotFoundException(PROFILE_WITH_ID_NOT_FOUND.formatted(id))));
    }

    public ProfileResponse insert(ProfileRequest profileRequest) {
        String login = profileRequest.getLogin();
        if (profileRepository.existsByLogin(login)) {
            throw new ProfileAlreadyExistsException(PROFILE_WITH_LOGIN_ALREADY_EXISTS.formatted(login));
        }

        Profile profile = getProfileWithEncodedPassword(profileRequest);
        Profile result = profileRepository.save(profile);
        return profileMapper.mapToProfileResponse(result);
    }

    public ProfileResponse update(Long id, ProfileRequest profileRequest) {
        if (!profileRepository.existsById(id)) {
            throw new NotFoundException(PROFILE_WITH_ID_NOT_FOUND.formatted(id));
        }

        String login = profileRequest.getLogin();
        Optional<Profile> profileWithSameLogin = profileRepository.findProfileByLogin(login);
        if (profileWithSameLogin.isPresent() && !Objects.equals(profileWithSameLogin.get().getId(), id)) {
            throw new ProfileAlreadyExistsException(PROFILE_WITH_LOGIN_ALREADY_EXISTS.formatted(login));
        }

        Profile profile = getProfileWithEncodedPasswordAndId(id, profileRequest);
        Profile result = profileRepository.save(profile);
        return profileMapper.mapToProfileResponse(result);
    }

    public void delete(Long id) {
        if (!profileRepository.existsById(id)) {
            throw new NotFoundException(PROFILE_WITH_ID_NOT_FOUND.formatted(id));
        }

        profileRepository.deleteById(id);
    }

    private Profile getProfileWithEncodedPassword(ProfileRequest profileRequest) {
        Profile profile = profileMapper.mapToProfile(profileRequest);
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        return profile;
    }

    private Profile getProfileWithEncodedPasswordAndId(Long id, ProfileRequest profileRequest) {
        Profile profile = profileMapper.mapToProfile(id, profileRequest);
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        return profile;
    }
}
