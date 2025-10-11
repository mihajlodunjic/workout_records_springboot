package org.fon.workout_records.controller;

import jakarta.validation.Valid;
import org.fon.workout_records.dto.TrainingRecordDto;
import org.fon.workout_records.service.TrainingRecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/training-record")
public class TrainingRecordController {

    private final TrainingRecordService service;

    public TrainingRecordController(TrainingRecordService service) {
        this.service = service;
    }

    // CRUD
    @GetMapping
    public ResponseEntity<List<TrainingRecordDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingRecordDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TrainingRecordDto> create(@Valid @RequestBody TrainingRecordDto dto) {
        TrainingRecordDto saved = service.create(dto);
        return ResponseEntity.created(URI.create("/api/training-record/" + saved.getRecordId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingRecordDto> update(@PathVariable Long id, @Valid @RequestBody TrainingRecordDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Jedinstvena SEARCH ruta (svi parametri su opcioni)
    // Primeri:
    //  - /api/training-record/search?trainerId=1
    //  - /api/training-record/search?clientId=2
    //  - /api/training-record/search?from=2025-10-01&to=2025-10-31
    //  - /api/training-record/search?trainerId=1&from=2025-10-01&to=2025-10-31
    //  - /api/training-record/search?trainerId=1&clientId=2&from=2025-10-01&to=2025-10-31
    @GetMapping("/search")
    public ResponseEntity<List<TrainingRecordDto>> search(
            @RequestParam(required = false) Long trainerId,
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        // Validacija opsega datuma (ako je zadat)
        if (from != null && to != null && from.isAfter(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "`from` must be <= `to`");
        }

        // 1) Odaberi bazni skup prema najrestriktivnijem dostupnom kriterijumu
        List<TrainingRecordDto> base;
        if (from != null && to != null) {
            base = service.byRange(from, to);
        } else if (trainerId != null) {
            base = service.byTrainer(trainerId);
        } else if (clientId != null) {
            base = service.byClient(clientId);
        } else {
            base = service.findAll();
        }

        // 2) Dodatno filtriraj po preostalim kriterijumima (ako su zadati)
        if (trainerId != null) {
            base = base.stream()
                    .filter(r -> trainerId.equals(r.getTrainerId()))
                    .toList();
        }
        if (clientId != null) {
            base = base.stream()
                    .filter(r -> clientId.equals(r.getClientId()))
                    .toList();
        }

        return ResponseEntity.ok(base);
    }
}
