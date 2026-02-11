package com.example.auctionsystem.service;

import com.example.auctionsystem.dto.BidMessage;
import com.example.auctionsystem.model.Auction;
import com.example.auctionsystem.model.Bid;
import com.example.auctionsystem.repository.AuctionRepository;
import com.example.auctionsystem.repository.BidRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    public AuctionService(AuctionRepository auctionRepository, BidRepository bidRepository) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    public Auction getAuction(Long auctionId) {
        return auctionRepository.findById(auctionId)
                .orElseThrow(() -> new IllegalArgumentException("Auction not found: " + auctionId));
    }

    @Transactional
    public Auction placeBid(BidMessage message, String bidderName) {
        Auction auction = getAuction(message.getAuctionId());

        if (auction.getEndTime() != null && auction.getEndTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Auction has ended");
        }

        BigDecimal current = auction.getCurrentPrice() == null ? auction.getStartingPrice() : auction.getCurrentPrice();
        if (message.getAmount().compareTo(current) <= 0) {
            throw new IllegalArgumentException("Bid must be higher than current price");
        }

        Bid bid = new Bid();
        bid.setBidderName(bidderName);
        bid.setAmount(message.getAmount());
        bid.setBidTime(LocalDateTime.now());
        bid.setAuction(auction);

        auction.setCurrentPrice(message.getAmount());
        bidRepository.save(bid);

        return auctionRepository.save(auction);
    }

    @Transactional
    public Auction createAuction(Auction auction) {
        if (auction.getCurrentPrice() == null) {
            auction.setCurrentPrice(auction.getStartingPrice());
        }
        return auctionRepository.save(auction);
    }
}
