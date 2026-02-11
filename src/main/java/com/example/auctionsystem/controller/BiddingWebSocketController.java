package com.example.auctionsystem.controller;

import com.example.auctionsystem.dto.BidMessage;
import com.example.auctionsystem.model.Auction;
import com.example.auctionsystem.service.AuctionService;
import jakarta.validation.Valid;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class BiddingWebSocketController {

    private final AuctionService auctionService;
    private final SimpMessagingTemplate messagingTemplate;

    public BiddingWebSocketController(AuctionService auctionService, SimpMessagingTemplate messagingTemplate) {
        this.auctionService = auctionService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/bid")
    public void placeBid(@Valid @Payload BidMessage bidMessage, Principal principal) {
        try {
            if (principal == null) {
                throw new IllegalStateException("You must be logged in to place a bid");
            }

            Auction updatedAuction = auctionService.placeBid(bidMessage, principal.getName());
            messagingTemplate.convertAndSend("/topic/auctions/" + updatedAuction.getId(), updatedAuction.getCurrentPrice());
        } catch (RuntimeException ex) {
            messagingTemplate.convertAndSend("/topic/auctions/" + bidMessage.getAuctionId() + "/errors", ex.getMessage());
        }
    }
}
