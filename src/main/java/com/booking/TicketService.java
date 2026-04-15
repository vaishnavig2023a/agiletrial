package com.booking;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class TicketService {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", TicketService::handleHome);
        server.createContext("/book", TicketService::handleBook);

        server.setExecutor(null);
        server.start();

        System.out.println("Server started on http://localhost:8080");
    }

    private static void handleHome(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Method Not Allowed", "text/plain; charset=UTF-8");
            return;
        }

        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Ticket Booking</title>
                    <style>
                        * {
                            box-sizing: border-box;
                        }
                        body {
                            margin: 0;
                            font-family: Arial, sans-serif;
                            background: #f4f6f8;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            min-height: 100vh;
                        }
                        .container {
                            width: 90%;
                            max-width: 420px;
                            background: #ffffff;
                            padding: 30px;
                            border-radius: 12px;
                            box-shadow: 0 4px 14px rgba(0, 0, 0, 0.12);
                            text-align: center;
                        }
                        h1 {
                            margin-top: 0;
                            color: #222;
                        }
                        p {
                            color: #555;
                            margin-bottom: 20px;
                        }
                        button {
                            background: #007bff;
                            color: #fff;
                            border: none;
                            padding: 12px 22px;
                            font-size: 16px;
                            border-radius: 6px;
                            cursor: pointer;
                        }
                        button:hover {
                            background: #0056b3;
                        }
                        #result {
                            margin-top: 20px;
                            font-weight: bold;
                            color: green;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Ticket Booking App</h1>
                        <p>Click the button to book your ticket.</p>
                        <button onclick="bookTicket()">Book Ticket</button>
                        <div id="result"></div>
                    </div>

                    <script>
                        function bookTicket() {
                            fetch('/book')
                                .then(function(response) {
                                    return response.text();
                                })
                                .then(function(data) {
                                    document.getElementById('result').innerText = data;
                                })
                                .catch(function(error) {
                                    document.getElementById('result').innerText = 'Error: ' + error;
                                });
                        }
                    </script>
                </body>
                </html>
                """;

        sendResponse(exchange, 200, html, "text/html; charset=UTF-8");
    }

    private static void handleBook(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Method Not Allowed", "text/plain; charset=UTF-8");
            return;
        }

        sendResponse(exchange, 200, "Ticket booked successfully", "text/plain; charset=UTF-8");
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String body, String contentType)
            throws IOException {
        byte[] responseBytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(responseBytes);
        }
    }

    public String getBookingMessage() {
        return "Ticket booked successfully";
    }

    public String getHomePage() {
        return """
                <!DOCTYPE html>
                <html>
                <head><title>Ticket Booking</title></head>
                <body><h1>Ticket Booking App</h1></body>
                </html>
                """;
    }
}