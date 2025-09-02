package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Value("${app.shortener.base-url:http://localhost:8080}")
    private String baseUrl;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        String html = "<html><body style=\"font-family:Arial,Helvetica,sans-serif;\">"
                + "<h2>URL Shortener API</h2>"
                + "<p>API endpoints:</p>"
                + "<ul>"
                + "<li>POST <code>/api/v1/shortener</code> — create short URL</li>"
                + "<li>Actuator: <a href=\"/actuator/health\">/actuator/health</a></li>"
                + "</ul>"
                + "<p>Base URL: " + baseUrl + "</p>"
                + "</body></html>";
        return ResponseEntity.ok().header("Content-Type", "text/html; charset=UTF-8").body(html);
    }
}
