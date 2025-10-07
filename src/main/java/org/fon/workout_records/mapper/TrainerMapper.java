package org.fon.workout_records.mapper;


import org.fon.workout_records.dto.TrainerDto;
import org.fon.workout_records.entity.Trainer;
import org.springframework.stereotype.Component;

@Component
public class TrainerMapper implements DtoEntityMapper<TrainerDto, Trainer> {
    @Override
    public TrainerDto toDto(Trainer e) {
        if (e == null) return null;
        TrainerDto d = new TrainerDto();
        d.setTrainerId(e.getTrainerId());
        d.setFirstName(e.getFirstName());
        d.setLastName(e.getLastName());
        d.setUsername(e.getUsername());
        return d;
    }
    @Override
    public Trainer toEntity(TrainerDto d) {
        if (d == null) return null;
        Trainer e = new Trainer();
        e.setTrainerId(d.getTrainerId());
        e.setFirstName(d.getFirstName());
        e.setLastName(d.getLastName());
        e.setUsername(d.getUsername());
        return e;
    }
}

