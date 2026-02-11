package com.example.auctionsystem.controller;

import com.example.auctionsystem.service.AuctionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        model.addAttribute("auctions", auctionService.getAllAuctions());
        model.addAttribute("username", principal != null ? principal.getName() : "Guest");
        return "index";
    }

    @GetMapping("/auctions/{id}")
    public String auctionDetail(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("auction", auctionService.getAuction(id));
        model.addAttribute("username", principal != null ? principal.getName() : "Guest");
        return "auction";
    }
}
