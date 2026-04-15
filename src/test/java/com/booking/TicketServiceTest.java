package com.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketServiceTest {

    @Test
    void testBookingMessage() {
        TicketService service = new TicketService();
        assertEquals("Ticket booked successfully", service.getBookingMessage());
    }

    @Test
    void testHomePageContainsHtml() {
        TicketService service = new TicketService();
        String html = service.getHomePage();

        assertTrue(html.contains("<html>") || html.contains("<!DOCTYPE html>"));
        assertTrue(html.contains("Ticket Booking App"));
    }
}