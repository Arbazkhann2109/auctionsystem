package com.example.auctionsystem.controller;

import com.example.auctionsystem.model.Auction;
import com.example.auctionsystem.service.AuctionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("auctions", auctionService.getAllAuctions());
        return "index";
    }

    @GetMapping("/auctions/{id}")
    public String auctionDetail(@PathVariable Long id, Model model) {
        model.addAttribute("auction", auctionService.getAuction(id));
        return "auction";
    }
}
