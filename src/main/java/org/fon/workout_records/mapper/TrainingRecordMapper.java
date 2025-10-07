package org.fon.workout_records.mapper;


import org.fon.workout_records.dto.TrainingItemDto;
import org.fon.workout_records.dto.TrainingRecordDto;
import org.fon.workout_records.entity.Client;
import org.fon.workout_records.entity.Trainer;
import org.fon.workout_records.entity.TrainingItem;
import org.fon.workout_records.entity.TrainingRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainingRecordMapper implements DtoEntityMapper<TrainingRecordDto, TrainingRecord> {

    private final TrainingItemMapper itemMapper;

    public TrainingRecordMapper(TrainingItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public TrainingRecordDto toDto(TrainingRecord e) {
        if (e == null) return null;

        List<TrainingItemDto> items = null;
        if (e.getItems() != null) {
            items = e.getItems().stream().map(itemMapper::toDto).collect(Collectors.toList());
        }

        TrainingRecordDto d = new TrainingRecordDto();
        d.setRecordId(e.getRecordId());
        d.setTrainingDate(e.getTrainingDate());
        d.setStartTime(e.getStartTime());
        d.setEndTime(e.getEndTime());
        d.setDurationMinutes(e.getDurationMinutes());
        d.setAverageIntensity(e.getAverageIntensity());
        d.setTrainerId(e.getTrainer() != null ? e.getTrainer().getTrainerId() : null);
        d.setClientId(e.getClient() != null ? e.getClient().getClientId() : null);
        d.setItems(items);
        return d;
    }

    @Override
    public TrainingRecord toEntity(TrainingRecordDto d) {
        if (d == null) return null;
        TrainingRecord e = new TrainingRecord();
        e.setRecordId(d.getRecordId());
        e.setTrainingDate(d.getTrainingDate());
        e.setStartTime(d.getStartTime());
        e.setEndTime(d.getEndTime());
        e.setDurationMinutes(d.getDurationMinutes());
        e.setAverageIntensity(d.getAverageIntensity());
        if (d.getTrainerId() != null) e.setTrainer(new Trainer() {{ setTrainerId(d.getTrainerId()); }});
        if (d.getClientId() != null) e.setClient(new Client() {{ setClientId(d.getClientId()); }});

        if (d.getItems() != null) {
            for (TrainingItemDto idto : d.getItems()) {
                TrainingItem item = itemMapper.toEntity(idto);
                item.setRecord(e);            // backref
                e.getItems().add(item);
            }
        }
        return e;
    }
}

