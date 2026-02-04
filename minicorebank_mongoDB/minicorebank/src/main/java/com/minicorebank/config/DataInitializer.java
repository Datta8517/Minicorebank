package com.minicorebank.config;

import com.minicorebank.model.Role;
import com.minicorebank.model.User;
import com.minicorebank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() == 0) {
            userRepository.save(new User("admin",
                    passwordEncoder.encode("admin123"), Role.ADMIN, true));

            userRepository.save(new User("Dattatray",
                    passwordEncoder.encode("Dattatray123"), Role.CUSTOMER,true));

            userRepository.save(new User("Pawan",
                    passwordEncoder.encode("Pawan123"), Role.CUSTOMER,true));
        }
    }
}

