package org.fon.workout_records.controller;

import jakarta.validation.Valid;
import org.fon.workout_records.dto.ActivityDto;
import org.fon.workout_records.entity.enums.ActivityCategory;
import org.fon.workout_records.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ActivityDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ActivityDto>> byCategory(@PathVariable ActivityCategory category) {
        return ResponseEntity.ok(service.findByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ActivityDto>> search(@RequestParam("q") String q) {
        return ResponseEntity.ok(service.searchByName(q));
    }

    @PostMapping
    public ResponseEntity<ActivityDto> create(@Valid @RequestBody ActivityDto dto) {
        ActivityDto saved = service.create(dto);
        return ResponseEntity.created(URI.create("/api/activity/" + saved.getActivityId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDto> update(@PathVariable Long id, @Valid @RequestBody ActivityDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

