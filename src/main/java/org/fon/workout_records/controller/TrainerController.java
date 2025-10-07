package org.fon.workout_records.controller;



import jakarta.validation.Valid;
import org.fon.workout_records.dto.TrainerDto;
import org.fon.workout_records.service.TrainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/trainer")
public class TrainerController {

    private final TrainerService service;
    public TrainerController(TrainerService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<List<TrainerDto>> all() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> one(@PathVariable Long id) { return ResponseEntity.ok(service.findById(id)); }

    // kreiranje preko CRUD rute (ako želiš mimo /auth/register)
    @PostMapping
    public ResponseEntity<TrainerDto> create(@Valid @RequestBody TrainerDto dto) {
        TrainerDto saved = service.create(dto, "changeme"); // ili u body dodaj password field
        return ResponseEntity.created(URI.create("/api/trainer/" + saved.getTrainerId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerDto> update(@PathVariable Long id, @Valid @RequestBody TrainerDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

