package com.app.validation;

import com.app.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class ProfileExistsValidator implements ConstraintValidator<ProfileExists, Long> {
    private final ProfileRepository profileRepository;

    @Override
    public boolean isValid(Long idProfile, ConstraintValidatorContext constraintValidatorContext) {
        if (idProfile == null) {
            return false;
        }

        return profileRepository.existsById(idProfile);
    }
}
