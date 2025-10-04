package org.fon.workout_records.service;


import org.fon.workout_records.dto.ActivityDto;
import org.fon.workout_records.entity.Activity;
import org.fon.workout_records.entity.enums.ActivityCategory;
import org.fon.workout_records.mapper.ActivityMapper;
import org.fon.workout_records.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository repo;
    private final ActivityMapper mapper;

    public ActivityService(ActivityRepository repo, ActivityMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<ActivityDto> findAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public ActivityDto findById(Long id) {
        Activity a = repo.findById(id).orElseThrow(() -> new RuntimeException("Activity not found: " + id));
        return mapper.toDto(a);
    }

    public List<ActivityDto> findByCategory(ActivityCategory category) {
        return repo.findByCategory(category).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<ActivityDto> searchByName(String q) {
        return repo.findByNameContainingIgnoreCase(q).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public ActivityDto create(ActivityDto dto) {
        if (dto.getName() != null && repo.existsByNameIgnoreCase(dto.getName())) {
            throw new RuntimeException("Activity with that name already exists: " + dto.getName());
        }
        Activity a = mapper.toEntity(dto);
        repo.save(a);
        return mapper.toDto(a);
    }

    public ActivityDto update(Long id, ActivityDto dto) {
        repo.findById(id).orElseThrow(() -> new RuntimeException("Activity not found: " + id));
        dto.setActivityId(id);
        Activity a = mapper.toEntity(dto);
        repo.save(a);
        return mapper.toDto(a);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

