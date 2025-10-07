package org.fon.workout_records.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.fon.workout_records.entity.enums.Intensity;

public class TrainingItemDto {
    private Long itemId;

    private Integer itemNo;

    @NotNull
    private Intensity intensity;

    @Min(0)
    private Integer sets;

    private Double weight;

    @Size(max = 500)
    private String note;

    @NotNull
    private Long activityId;

    // getters/setters
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public Integer getItemNo() { return itemNo; }
    public void setItemNo(Integer itemNo) { this.itemNo = itemNo; }
    public Intensity getIntensity() { return intensity; }
    public void setIntensity(Intensity intensity) { this.intensity = intensity; }
    public Integer getSets() { return sets; }
    public void setSets(Integer sets) { this.sets = sets; }
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }
}

