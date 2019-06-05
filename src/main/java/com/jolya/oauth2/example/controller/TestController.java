package com.jolya.oauth2.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class TestController {

    private String defaultName ="";

    @GetMapping("/hello/{name}")
    public ResponseEntity<String> helloYou(@PathVariable String name) {
        return ResponseEntity.ok().body("Hello "+name);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloYou() {
        return ResponseEntity.ok().body("Hello "+defaultName);
    }

    @PostMapping("/hello")
    public ResponseEntity getAllConfiguration(@PathVariable String name) {
        defaultName = name;
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/hello")
    public ResponseEntity getAllConfiguration() {
        defaultName = "";
        return ResponseEntity.ok().build();
    }
}
