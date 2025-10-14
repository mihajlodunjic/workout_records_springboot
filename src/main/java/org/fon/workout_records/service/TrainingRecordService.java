package org.fon.workout_records.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.fon.workout_records.dto.TrainingRecordDto;
import org.fon.workout_records.entity.Activity;
import org.fon.workout_records.entity.Client;
import org.fon.workout_records.entity.Trainer;
import org.fon.workout_records.entity.TrainingRecord;
import org.fon.workout_records.mapper.TrainingRecordMapper;
import org.fon.workout_records.repository.TrainingRecordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class TrainingRecordService {

    private final TrainingRecordRepository repo;
    private final TrainingRecordMapper mapper;

    @PersistenceContext
    private EntityManager em;

    public TrainingRecordService(TrainingRecordRepository repo, TrainingRecordMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    // CRUD
    public List<TrainingRecordDto> findAll() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public TrainingRecordDto findById(Long id) {
        TrainingRecord r = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id));
        return mapper.toDto(r);
    }
    @Transactional
    public TrainingRecordDto create(TrainingRecordDto dto) {
        // validate refs
        if (em.find(Trainer.class, dto.getTrainerId()) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer not found: " + dto.getTrainerId());
        if (em.find(Client.class, dto.getClientId()) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found: " + dto.getClientId());

        // map
        TrainingRecord r = mapper.toEntity(dto);
        r.setTrainer(em.getReference(Trainer.class, dto.getTrainerId()));
        r.setClient(em.getReference(Client.class, dto.getClientId()));
        // items already attached by mapper; set activities to references:
        if (r.getItems() != null) {
            r.getItems().forEach(it -> {
                if (it.getActivity() == null || it.getActivity().getActivityId() == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ActivityId is required for each item.");
                }
                // (opciono) potvrdi da aktivnost postoji:
                if (em.find(Activity.class, it.getActivity().getActivityId()) == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found: " + it.getActivity().getActivityId());
                }
                it.setActivity(em.getReference(Activity.class, it.getActivity().getActivityId()));
            });
        }

        repo.save(r);
        return mapper.toDto(r);
    }
    @Transactional
    public TrainingRecordDto update(Long id, TrainingRecordDto dto) {
        repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found: " + id));
        dto.setRecordId(id);
        return createOrMerge(dto);
    }

    private TrainingRecordDto createOrMerge(TrainingRecordDto dto) {
        TrainingRecord r = mapper.toEntity(dto);
        r.setTrainer(em.getReference(Trainer.class, dto.getTrainerId()));
        r.setClient(em.getReference(Client.class, dto.getClientId()));
        if (r.getItems() != null) {
            r.getItems().forEach(it -> {
                if (it.getActivity() == null || it.getActivity().getActivityId() == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ActivityId is required for each item.");
                }
                if (em.find(Activity.class, it.getActivity().getActivityId()) == null) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found: " + it.getActivity().getActivityId());
                }
                it.setActivity(em.getReference(Activity.class, it.getActivity().getActivityId()));
            });
        }
        repo.save(r);
        return mapper.toDto(r);
    }
    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // Filters
    public List<TrainingRecordDto> byTrainer(Long trainerId) {
        return repo.findByTrainer_TrainerIdOrderByTrainingDateDesc(trainerId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<TrainingRecordDto> byClient(Long clientId) {
        return repo.findByClient_ClientIdOrderByTrainingDateDesc(clientId)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<TrainingRecordDto> byRange(LocalDate from, LocalDate to) {
        return repo.findByTrainingDateBetweenOrderByTrainingDateDesc(from, to)
                .stream().map(mapper::toDto).collect(Collectors.toList());
    }
}

