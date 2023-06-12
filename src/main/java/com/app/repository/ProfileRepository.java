package com.app.repository;

import com.app.entity.Profile;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
    @NonNull
    List<Profile> findAll();

    boolean existsByLogin(String login);

    Optional<Profile> findProfileById(Long id);

    Optional<Profile> findProfileByLogin(String login);
}
