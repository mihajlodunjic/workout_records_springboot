package org.fon.workout_records.mapper;



import org.fon.workout_records.dto.GymDto;
import org.fon.workout_records.entity.Gym;
import org.springframework.stereotype.Component;

@Component
public class GymMapper implements DtoEntityMapper<GymDto, Gym> {

    @Override
    public GymDto toDto(Gym e) {
        if (e == null) return null;
        GymDto d = new GymDto();
        d.setGymId(e.getGymId());
        d.setName(e.getName());
        d.setAddress(e.getAddress());
        d.setEquipmentLevel(e.getEquipmentLevel());
        d.setPhone(e.getPhone());
        d.setEmail(e.getEmail());
        return d;
    }

    @Override
    public Gym toEntity(GymDto d) {
        if (d == null) return null;
        Gym e = new Gym();
        e.setGymId(d.getGymId());
        e.setName(d.getName());
        e.setAddress(d.getAddress());
        e.setEquipmentLevel(d.getEquipmentLevel());
        e.setPhone(d.getPhone());
        e.setEmail(d.getEmail());
        return e;
    }
}

