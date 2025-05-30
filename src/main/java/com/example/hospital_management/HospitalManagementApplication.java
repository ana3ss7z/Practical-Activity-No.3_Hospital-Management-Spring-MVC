package com.example.hospital_management;

import com.example.hospital_management.entites.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.hospital_management.repositories.PatientRepository;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class HospitalManagementApplication {
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementApplication.class, args);
    }

//    @Bean
    CommandLineRunner start(PatientRepository patientRepository) {
        return args -> {
            Patient p1 = Patient.builder()
                    .nom("Anass")
                    .dateNaissance(new Date())
                    .score(23)
                    .malade(false)
                    .build();
            patientRepository.save(p1);
            patientRepository.save(new Patient(null,"aymane",new Date(),true,200));
            patientRepository.save(new Patient(null,"yousra",new Date(),true,54));
            patientRepository.save(new Patient(null,"SalahEddin",new Date(),false,82));
        };
    }
}
