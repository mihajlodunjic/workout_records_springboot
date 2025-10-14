package org.fon.workout_records.service;


import org.fon.workout_records.dto.TrainerDto;
import org.fon.workout_records.dto.auth.LoginRequest;
import org.fon.workout_records.dto.auth.LoginResponse;
import org.fon.workout_records.dto.auth.RegisterRequest;
import org.fon.workout_records.entity.Trainer;
import org.fon.workout_records.mapper.TrainerMapper;
import org.fon.workout_records.repository.TrainerRepository;
import org.fon.workout_records.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class TrainerService {

    private final TrainerRepository repo;
    private final TrainerMapper mapper;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    public TrainerService(TrainerRepository repo, TrainerMapper mapper,
                          PasswordEncoder encoder, JwtUtil jwt) {
        this.repo = repo; this.mapper = mapper;
        this.encoder = encoder; this.jwt = jwt;
    }

    // CRUD
    public List<TrainerDto> findAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    public TrainerDto findById(Long id) {
        return mapper.toDto(repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer not found")));
    }
    @Transactional
    public TrainerDto create(TrainerDto dto, String rawPassword) {
        if (repo.existsByUsername(dto.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username taken");
        Trainer t = mapper.toEntity(dto);
        t.setPasswordHash(encoder.encode(rawPassword));
        repo.save(t);
        return mapper.toDto(t);
    }
    @Transactional
    public TrainerDto update(Long id, TrainerDto dto) {
        Trainer existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainer not found"));
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        repo.save(existing);
        return mapper.toDto(existing);
    }
    @Transactional
    public void delete(Long id) { repo.deleteById(id); }

    // Auth
    @Transactional
    public LoginResponse register(RegisterRequest req) {
        TrainerDto dto = new TrainerDto();
        dto.setFirstName(req.getFirstName());
        dto.setLastName(req.getLastName());
        dto.setUsername(req.getUsername());
        TrainerDto saved = create(dto, req.getPassword());
        String token = jwt.generate(saved.getUsername());
        return new LoginResponse(token, saved.getUsername(), saved.getTrainerId());
    }

    public LoginResponse login(LoginRequest req) {
        Trainer t = repo.findByUsername(req.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials"));
        if (!encoder.matches(req.getPassword(), t.getPasswordHash()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        String token = jwt.generate(t.getUsername());
        return new LoginResponse(token, t.getUsername(), t.getTrainerId());
    }
}

