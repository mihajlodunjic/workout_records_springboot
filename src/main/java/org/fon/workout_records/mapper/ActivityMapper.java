package org.fon.workout_records.mapper;


import org.fon.workout_records.dto.ActivityDto;
import org.fon.workout_records.entity.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper implements DtoEntityMapper<ActivityDto, Activity> {

    @Override
    public ActivityDto toDto(Activity e) {
        if (e == null) return null;
        ActivityDto d = new ActivityDto();
        d.setActivityId(e.getActivityId());
        d.setCategory(e.getCategory());
        d.setName(e.getName());
        return d;
    }

    @Override
    public Activity toEntity(ActivityDto d) {
        if (d == null) return null;
        Activity e = new Activity();
        e.setActivityId(d.getActivityId());
        e.setCategory(d.getCategory());
        e.setName(d.getName());
        return e;
    }
}

