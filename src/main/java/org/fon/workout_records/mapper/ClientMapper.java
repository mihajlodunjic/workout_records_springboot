package org.fon.workout_records.mapper;

import org.fon.workout_records.dto.ClientDto;
import org.fon.workout_records.entity.Client;
import org.fon.workout_records.entity.Gym;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper implements DtoEntityMapper<ClientDto, Client> {

    @Override
    public ClientDto toDto(Client e) {
        if (e == null) return null;
        ClientDto d = new ClientDto();
        d.setClientId(e.getClientId());
        d.setFirstName(e.getFirstName());
        d.setLastName(e.getLastName());
        d.setGender(e.getGender());
        d.setBirthDate(e.getBirthDate());
        d.setPhone(e.getPhone());
        d.setGymId(e.getGym() != null ? e.getGym().getGymId() : null);
        return d;
    }

    @Override
    public Client toEntity(ClientDto d) {
        if (d == null) return null;
        Client e = new Client();
        e.setClientId(d.getClientId());
        e.setFirstName(d.getFirstName());
        e.setLastName(d.getLastName());
        e.setGender(d.getGender());
        e.setBirthDate(d.getBirthDate());
        e.setPhone(d.getPhone());
        if (d.getGymId() != null) {
            Gym g = new Gym();
            g.setGymId(d.getGymId());
            e.setGym(g);
        }
        return e;
    }
}

