package com.paymybuddy.paymybuddy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PayMyBuddyApp implements CommandLineRunner {

    @Autowired
    private PasswordEncoder encoder;

    public static void main(String[] args) {
        SpringApplication.run(PayMyBuddyApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final String encoded = encoder.encode("passer@123");
        System.out.println("## " + encoded);
    }
}