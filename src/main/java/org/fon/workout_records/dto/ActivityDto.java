package org.fon.workout_records.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.fon.workout_records.entity.enums.ActivityCategory;

public class ActivityDto {
    private Long activityId;

    @NotNull
    private ActivityCategory category;

    @NotBlank
    @Size(max = 120)
    private String name;

    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }
    public ActivityCategory getCategory() { return category; }
    public void setCategory(ActivityCategory category) { this.category = category; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
