package com.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuctionServiceTest {

    private AuctionService service;

    @BeforeEach
    void setup() {
        service = new AuctionService();
        service.addItem(1L, 500.0);
    }

    @Test
    void validBidShouldPass() {
        String result = service.placeBid(1L, "Alice", 600.0);
        assertEquals("Bid placed successfully by Alice for amount 600.0", result);
    }

    @Test
    void rejectZeroAmount() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> service.placeBid(1L, "Bob", 0));

        assertEquals("Bid amount must be greater than zero.", ex.getMessage());
    }

    @Test
    void rejectEmptyName() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> service.placeBid(1L, "", 700));

        assertEquals("Bidder name cannot be empty.", ex.getMessage());
    }

    @Test
    void rejectInvalidItem() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> service.placeBid(99L, "John", 700));

        assertEquals("Invalid item ID.", ex.getMessage());
    }

    @Test
    void rejectLowerBid() {
        service.placeBid(1L, "Alice", 700);

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> service.placeBid(1L, "Bob", 650));

        assertEquals("Bid must be greater than current highest bid or starting price.",
                ex.getMessage());
    }

    @Test
    void highestBidShouldBeCorrect() {
        service.placeBid(1L, "A", 700);
        service.placeBid(1L, "B", 800);
        service.placeBid(1L, "C", 900);

        AuctionService.Bid highest = service.getHighestBid(1L);

        assertNotNull(highest);
        assertEquals("C", highest.getBidder());
        assertEquals(900, highest.getAmount());
    }
}