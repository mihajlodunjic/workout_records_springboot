package org.fon.workout_records.entity;


import jakarta.persistence.*;
import org.fon.workout_records.entity.enums.Intensity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "training_record",
        indexes = @Index(name = "idx_record_date", columnList = "training_date"))
public class TrainingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    @Column(name = "training_date", nullable = false)
    private LocalDate trainingDate;

    private LocalTime startTime;
    private LocalTime endTime;
    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Intensity averageIntensity = Intensity.MEDIUM;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingItem> items = new ArrayList<>();

    // helpers
    public void addItem(TrainingItem item) {
        item.setRecord(this);
        this.items.add(item);
    }
    public void removeItem(TrainingItem item) {
        item.setRecord(null);
        this.items.remove(item);
    }

    // getters & setters
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
    public Trainer getTrainer() { return trainer; }
    public void setTrainer(Trainer trainer) { this.trainer = trainer; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public List<TrainingItem> getItems() { return items; }
    public void setItems(List<TrainingItem> items) { this.items = items; }
}

