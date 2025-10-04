package org.fon.workout_records.repository;


import org.fon.workout_records.entity.Activity;
import org.fon.workout_records.entity.enums.ActivityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    boolean existsByNameIgnoreCase(String name);
    List<Activity> findByCategory(ActivityCategory category);
    List<Activity> findByNameContainingIgnoreCase(String namePart);
}
