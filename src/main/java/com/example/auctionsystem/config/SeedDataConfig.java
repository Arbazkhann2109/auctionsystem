package com.example.auctionsystem.config;

import com.example.auctionsystem.model.AppUser;
import com.example.auctionsystem.model.Auction;
import com.example.auctionsystem.repository.AppUserRepository;
import com.example.auctionsystem.repository.AuctionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Configuration
public class SeedDataConfig {

    @Bean
    CommandLineRunner seedAuctions(AuctionRepository auctionRepository,
                                   AppUserRepository appUserRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            if (auctionRepository.count() == 0) {
                Auction camera = new Auction();
                camera.setTitle("Vintage Camera");
                camera.setDescription("Classic 35mm camera in good condition.");
                camera.setStartingPrice(BigDecimal.valueOf(100));
                camera.setCurrentPrice(BigDecimal.valueOf(100));
                camera.setEndTime(LocalDateTime.now().plusDays(1));

                Auction watch = new Auction();
                watch.setTitle("Luxury Wrist Watch");
                watch.setDescription("Automatic mechanical watch with leather strap.");
                watch.setStartingPrice(BigDecimal.valueOf(250));
                watch.setCurrentPrice(BigDecimal.valueOf(250));
                watch.setEndTime(LocalDateTime.now().plusHours(12));

                auctionRepository.save(camera);
                auctionRepository.save(watch);
            }

            if (!appUserRepository.existsByUsername("demo")) {
                AppUser user = new AppUser();
                user.setUsername("demo");
                user.setPassword(passwordEncoder.encode("password"));
                user.setRole("ROLE_USER");
                appUserRepository.save(user);
            }
        };
    }
}
