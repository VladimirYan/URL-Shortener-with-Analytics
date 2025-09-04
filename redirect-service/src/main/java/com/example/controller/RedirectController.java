package com.example.controller;

import com.example.service.RedirectService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final RedirectService redirectService;

    @GetMapping("/r/{shortKey}")
    public ResponseEntity<Void> redirect(@PathVariable String shortKey, HttpServletResponse response) throws IOException {
        String target = redirectService.resolveLongUrl(shortKey);
        if (target == null) {
            return ResponseEntity.notFound().build();
        }
        response.sendRedirect(target);
        return ResponseEntity.status(302).build();
    }
}
