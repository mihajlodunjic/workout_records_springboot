package org.fon.workout_records.repository;


import org.fon.workout_records.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByGym_GymId(Long gymId);

    List<Client> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    List<Client> findByLastNameIgnoreCaseAndFirstNameIgnoreCase(String lastName, String firstName);

    List<Client> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String lastNamePart, String firstNamePart);
}

