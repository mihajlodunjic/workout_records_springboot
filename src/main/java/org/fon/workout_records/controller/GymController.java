package org.fon.workout_records.controller;

import jakarta.validation.Valid;
import org.fon.workout_records.dto.GymDto;
import org.fon.workout_records.service.GymService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gym")
public class GymController {

    private final GymService service;

    public GymController(GymService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GymDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GymDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<GymDto> create(@Valid @RequestBody GymDto dto) {
        GymDto saved = service.create(dto);
        return ResponseEntity.created(URI.create("/api/gym/" + saved.getGymId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GymDto> update(@PathVariable Long id, @Valid @RequestBody GymDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

