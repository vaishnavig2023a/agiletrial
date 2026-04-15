package com.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TicketService {

    public static void main(String[] args) {
        SpringApplication.run(TicketService.class, args);
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Ticket Booking</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background: #f4f4f4;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            width: 400px;
                            margin: 80px auto;
                            background: white;
                            padding: 30px;
                            border-radius: 10px;
                            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
                            text-align: center;
                        }
                        h1 {
                            color: #333;
                        }
                        button {
                            background: #007bff;
                            color: white;
                            border: none;
                            padding: 12px 20px;
                            border-radius: 6px;
                            cursor: pointer;
                            font-size: 16px;
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
                        <p>Click the button to book your ticket</p>
                        <button onclick="bookTicket()">Book Ticket</button>
                        <div id="result"></div>
                    </div>

                    <script>
                        function bookTicket() {
                            fetch('/book')
                                .then(response => response.text())
                                .then(data => {
                                    document.getElementById('result').innerText = data;
                                })
                                .catch(error => {
                                    document.getElementById('result').innerText = 'Error: ' + error;
                                });
                        }
                    </script>
                </body>
                </html>
                """;
    }

    @GetMapping("/book")
    public String bookTicket() {
        return "Ticket booked successfully";
    }
}