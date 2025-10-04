package org.fon.workout_records.entity;

import jakarta.persistence.*;
import org.fon.workout_records.entity.enums.GymEquipmentLevel;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gym",
        uniqueConstraints = @UniqueConstraint(name = "uk_gym_name", columnNames = "name"))
public class Gym {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gymId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 200)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GymEquipmentLevel equipmentLevel = GymEquipmentLevel.BASIC;

    @Column(length = 40)
    private String phone;

    @Column(length = 120)
    private String email;

    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Client> clients = new ArrayList<>();

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
    public List<Client> getClients() { return clients; }
    public void setClients(List<Client> clients) { this.clients = clients; }
}