package org.fon.workout_records;

import org.fon.workout_records.entity.Trainer;
import org.fon.workout_records.repository.TrainerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EvidencijaTreningaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvidencijaTreningaApplication.class, args);
    }

    @Bean
    public CommandLineRunner testInsert(TrainerRepository trainerRepo) {
        return args -> {
            if (trainerRepo.count() == 0) {
                Trainer t = new Trainer();
                t.setFirstName("John");
                t.setLastName("Doe");
                t.setUsername("jdoe");
                t.setPassword("secret123");
                trainerRepo.save(t);
                System.out.println("✅ Test trainer inserted into DB!");
            }
        };
    }
}
