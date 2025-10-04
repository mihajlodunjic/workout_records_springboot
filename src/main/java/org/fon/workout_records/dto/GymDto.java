package org.fon.workout_records.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.fon.workout_records.entity.enums.GymEquipmentLevel;

public class GymDto {
    private Long gymId;

    @NotBlank
    @Size(max = 120)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String address;

    private GymEquipmentLevel equipmentLevel;

    @Size(max = 40)
    private String phone;

    @Size(max = 120)
    private String email;

    // getters & setters
    public Long getGymId() { return gymId; }
    public void setGymId(Long gymId) { this.gymId = gymId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public GymEquipmentLevel getEquipmentLevel() { return equipmentLevel; }
    public void setEquipmentLevel(GymEquipmentLevel equipmentLevel) { this.equipmentLevel = equipmentLevel; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
