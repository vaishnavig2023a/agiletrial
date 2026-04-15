package com.booking;

import java.util.*;

public class AuctionService {

    // Store bids per item
    private final Map<Long, List<Bid>> bids = new HashMap<>();

    // Store starting price
    private final Map<Long, Double> startingPrice = new HashMap<>();

    // Add item
    public void addItem(Long itemId, double price) {
        startingPrice.put(itemId, price);
        bids.putIfAbsent(itemId, new ArrayList<>());
    }

    // Place bid
    public String placeBid(Long itemId, String bidder, double amount) {

        if (!startingPrice.containsKey(itemId)) {
            throw new IllegalArgumentException("Invalid item ID.");
        }

        if (bidder == null || bidder.trim().isEmpty()) {
            throw new IllegalArgumentException("Bidder name cannot be empty.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Bid amount must be greater than zero.");
        }

        List<Bid> itemBids = bids.get(itemId);

        double currentMax = startingPrice.get(itemId);

        if (!itemBids.isEmpty()) {
            currentMax = itemBids.stream()
                    .mapToDouble(b -> b.amount)
                    .max()
                    .orElse(currentMax);
        }

        if (amount <= currentMax) {
            throw new IllegalArgumentException(
                    "Bid must be greater than current highest bid or starting price.");
        }

        Bid bid = new Bid(itemId, bidder, amount);
        itemBids.add(bid);

        return "Bid placed successfully by " + bidder + " for amount " + amount;
    }

    // Get highest bid
    public Bid getHighestBid(Long itemId) {
        List<Bid> itemBids = bids.get(itemId);

        if (itemBids == null || itemBids.isEmpty()) {
            return null;
        }

        return itemBids.stream()
                .max(Comparator.comparingDouble(b -> b.amount))
                .orElse(null);
    }

    // Inner Bid class (so no extra file)
    public static class Bid {
        Long itemId;
        String bidder;
        double amount;

        public Bid(Long itemId, String bidder, double amount) {
            this.itemId = itemId;
            this.bidder = bidder;
            this.amount = amount;
        }

        public String getBidder() {
            return bidder;
        }

        public double getAmount() {
            return amount;
        }
    }
}