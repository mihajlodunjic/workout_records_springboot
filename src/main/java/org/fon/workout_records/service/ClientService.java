package org.fon.workout_records.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.fon.workout_records.dto.ClientDto;
import org.fon.workout_records.entity.Client;
import org.fon.workout_records.entity.Gym;
import org.fon.workout_records.mapper.ClientMapper;
import org.fon.workout_records.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ClientService {

    private final ClientRepository repo;
    private final ClientMapper mapper;

    @PersistenceContext
    private EntityManager em;

    public ClientService(ClientRepository repo, ClientMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<ClientDto> findAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public ClientDto findById(Long id) {
        Client c = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found: " + id));
        return mapper.toDto(c);
    }

    public List<ClientDto> findByGym(Long gymId) {
        return repo.findByGym_GymId(gymId).stream().map(mapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public ClientDto create(ClientDto dto) {
        // validate gym exists
        Gym g = em.find(Gym.class, dto.getGymId());
        if (g == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gym not found: " + dto.getGymId());

        Client c = mapper.toEntity(dto);
        c.setGym(em.getReference(Gym.class, dto.getGymId()));
        repo.save(c);
        return mapper.toDto(c);
    }
    @Transactional
    public ClientDto update(Long id, ClientDto dto) {
        repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found: " + id));
        // validate gym
        Gym g = em.find(Gym.class, dto.getGymId());
        if (g == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gym not found: " + dto.getGymId());


        dto.setClientId(id);
        Client c = mapper.toEntity(dto);
        c.setGym(em.getReference(Gym.class, dto.getGymId()));
        repo.save(c);
        return mapper.toDto(c);
    }
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}

