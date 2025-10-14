package org.fon.workout_records.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.fon.workout_records.entity.enums.Intensity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TrainingRecordDto {
    private Long recordId;

    @NotNull
    private LocalDate trainingDate;

    private LocalTime startTime;
    private LocalTime endTime;
    private Integer durationMinutes;

    @NotNull
    private Intensity averageIntensity;

    @NotNull
    private Long trainerId;

    @NotNull
    private Long clientId;

    @Valid
    private List<TrainingItemDto> items;

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public LocalDate getTrainingDate() { return trainingDate; }
    public void setTrainingDate(LocalDate trainingDate) { this.trainingDate = trainingDate; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public Intensity getAverageIntensity() { return averageIntensity; }
    public void setAverageIntensity(Intensity averageIntensity) { this.averageIntensity = averageIntensity; }
    public Long getTrainerId() { return trainerId; }
    public void setTrainerId(Long trainerId) { this.trainerId = trainerId; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public List<TrainingItemDto> getItems() { return items; }
    public void setItems(List<TrainingItemDto> items) { this.items = items; }
}

