package com.hoangvu.spring_security.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldController {

    @GetMapping
    public ResponseEntity<Object> helloworld() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("message", "hello world");
        result.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
