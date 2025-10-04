package org.fon.workout_records.repository;


import org.fon.workout_records.entity.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GymRepository extends JpaRepository<Gym, Long> {
    boolean existsByNameIgnoreCase(String name);
    Optional<Gym> findByNameIgnoreCase(String name);
}

