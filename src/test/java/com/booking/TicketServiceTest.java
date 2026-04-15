package com.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketServiceTest {

    @Test
    void testBookTicketMessage() {
        TicketService service = new TicketService();
        assertEquals("Ticket booked successfully", service.bookTicket());
    }

    @Test
    void testHomePageContainsHtml() {
        TicketService service = new TicketService();
        String html = service.home();

        assertTrue(html.contains("<html"));
        assertTrue(html.contains("Ticket Booking App"));
        assertTrue(html.contains("button"));
    }
}