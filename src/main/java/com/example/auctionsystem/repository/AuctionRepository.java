package com.example.auctionsystem.repository;

import com.example.auctionsystem.model.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
}
