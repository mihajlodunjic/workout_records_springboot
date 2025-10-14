package org.fon.workout_records.entity;


import jakarta.persistence.*;
import org.fon.workout_records.entity.enums.Intensity;

@Entity
@Table(name = "training_item",
        indexes = @Index(name = "idx_item_record", columnList = "record_id"))
public class TrainingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(name = "item_no")
    private Integer itemNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Intensity intensity = Intensity.MEDIUM;

    private Integer sets;
    private Double weight;
    @Column(length = 500)
    private String note;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private TrainingRecord record;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

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
    public TrainingRecord getRecord() { return record; }
    public void setRecord(TrainingRecord record) { this.record = record; }
    public Activity getActivity() { return activity; }
    public void setActivity(Activity activity) { this.activity = activity; }
}

