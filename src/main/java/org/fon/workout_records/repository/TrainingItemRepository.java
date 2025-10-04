package org.fon.workout_records.repository;


import org.fon.workout_records.entity.TrainingItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingItemRepository extends JpaRepository<TrainingItem, Long> {

    List<TrainingItem> findByRecord_RecordIdOrderByItemNoAsc(Long recordId);

    long deleteByRecord_RecordId(Long recordId);
}

