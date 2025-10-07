package org.fon.workout_records.mapper;


import org.fon.workout_records.dto.TrainingItemDto;
import org.fon.workout_records.entity.Activity;
import org.fon.workout_records.entity.TrainingItem;
import org.springframework.stereotype.Component;

@Component
public class TrainingItemMapper implements DtoEntityMapper<TrainingItemDto, TrainingItem> {

    @Override
    public TrainingItemDto toDto(TrainingItem e) {
        if (e == null) return null;
        TrainingItemDto d = new TrainingItemDto();
        d.setItemId(e.getItemId());
        d.setItemNo(e.getItemNo());
        d.setIntensity(e.getIntensity());
        d.setSets(e.getSets());
        d.setWeight(e.getWeight());
        d.setNote(e.getNote());
        d.setActivityId(e.getActivity() != null ? e.getActivity().getActivityId() : null);
        return d;
    }

    @Override
    public TrainingItem toEntity(TrainingItemDto d) {
        if (d == null) return null;
        TrainingItem e = new TrainingItem();
        e.setItemId(d.getItemId());
        e.setItemNo(d.getItemNo());
        e.setIntensity(d.getIntensity());
        e.setSets(d.getSets());
        e.setWeight(d.getWeight());
        e.setNote(d.getNote());
        if (d.getActivityId() != null) {
            Activity a = new Activity();
            a.setActivityId(d.getActivityId());
            e.setActivity(a);
        }
        return e;
    }
}
