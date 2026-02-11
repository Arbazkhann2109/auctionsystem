package com.example.auctionsystem.repository;

import com.example.auctionsystem.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
