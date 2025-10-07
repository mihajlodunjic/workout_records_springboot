package org.fon.workout_records.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.fon.workout_records.entity.enums.Gender;

import java.time.LocalDate;

public class ClientDto {
    private Long clientId;

    @NotBlank @Size(max = 80)
    private String firstName;

    @NotBlank @Size(max = 80)
    private String lastName;

    @NotNull
    private Gender gender;

    private LocalDate birthDate;

    @Size(max = 40)
    private String phone;

    @NotNull
    private Long gymId;

    // getters/setters
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Long getGymId() { return gymId; }
    public void setGymId(Long gymId) { this.gymId = gymId; }
}
