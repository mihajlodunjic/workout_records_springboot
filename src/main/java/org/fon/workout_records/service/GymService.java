package org.fon.workout_records.service;


import org.fon.workout_records.dto.GymDto;
import org.fon.workout_records.entity.Gym;
import org.fon.workout_records.mapper.GymMapper;
import org.fon.workout_records.repository.GymRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class GymService {

    private final GymRepository repo;
    private final GymMapper mapper;

    public GymService(GymRepository repo, GymMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<GymDto> findAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public GymDto findById(Long id) {
        Gym g = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gym not found: " + id));
        return mapper.toDto(g);
    }
    @Transactional
    public GymDto create(GymDto dto) {
        if (dto.getName() != null && repo.existsByNameIgnoreCase(dto.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Gym with that name already exists: " + dto.getName());
        }

        Gym g = mapper.toEntity(dto);
        repo.save(g);
        return mapper.toDto(g);
    }
    @Transactional
    public GymDto update(Long id, GymDto dto) {
        repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gym not found: " + id));
        dto.setGymId(id);
        Gym g = mapper.toEntity(dto);
        repo.save(g);
        return mapper.toDto(g);
    }
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
