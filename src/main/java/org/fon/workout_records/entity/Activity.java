package org.fon.workout_records.entity;


import jakarta.persistence.*;
import org.fon.workout_records.entity.enums.ActivityCategory;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activity",
        uniqueConstraints = @UniqueConstraint(name = "uk_activity_name", columnNames = "name"))
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ActivityCategory category = ActivityCategory.OTHER;

    @Column(nullable = false, length = 120)
    private String name;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<TrainingItem> items = new ArrayList<>();

    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }
    public ActivityCategory getCategory() { return category; }
    public void setCategory(ActivityCategory category) { this.category = category; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<TrainingItem> getItems() { return items; }
    public void setItems(List<TrainingItem> items) { this.items = items; }
}

