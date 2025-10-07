package org.fon.workout_records.dto.auth;


public class LoginResponse {
    private String token;
    private String username;
    private Long trainerId;
    public LoginResponse(String token, String username, Long trainerId) {
        this.token = token; this.username = username; this.trainerId = trainerId;
    }
    public String getToken() { return token; }
    public String getUsername() { return username; }
    public Long getTrainerId() { return trainerId; }
}

