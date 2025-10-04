package org.fon.workout_records.repository;


import org.fon.workout_records.entity.TrainingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRecordRepository extends JpaRepository<TrainingRecord, Long> {

    // po treneru
    List<TrainingRecord> findByTrainer_TrainerIdOrderByTrainingDateDesc(Long trainerId);

    // po klijentu
    List<TrainingRecord> findByClient_ClientIdOrderByTrainingDateDesc(Long clientId);

    // po periodu (uključivo)
    List<TrainingRecord> findByTrainingDateBetweenOrderByTrainingDateDesc(LocalDate from, LocalDate to);

    // kombinacije (primer: trener + period)
    @Query("SELECT tr FROM TrainingRecord tr " +
            "WHERE tr.trainer.trainerId = :trainerId AND tr.trainingDate BETWEEN :from AND :to " +
            "ORDER BY tr.trainingDate DESC")
    List<TrainingRecord> findByTrainerAndRange(Long trainerId, LocalDate from, LocalDate to);

    // klijent + period
    @Query("SELECT tr FROM TrainingRecord tr " +
            "WHERE tr.client.clientId = :clientId AND tr.trainingDate BETWEEN :from AND :to " +
            "ORDER BY tr.trainingDate DESC")
    List<TrainingRecord> findByClientAndRange(Long clientId, LocalDate from, LocalDate to);
}
