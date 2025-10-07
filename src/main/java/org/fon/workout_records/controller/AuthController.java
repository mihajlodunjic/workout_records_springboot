package org.fon.workout_records.controller;


import org.fon.workout_records.dto.auth.LoginRequest;
import org.fon.workout_records.dto.auth.LoginResponse;
import org.fon.workout_records.dto.auth.RegisterRequest;
import org.fon.workout_records.service.TrainerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TrainerService service;
    public AuthController(TrainerService service) { this.service = service; }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(service.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(service.login(req));
    }
}

