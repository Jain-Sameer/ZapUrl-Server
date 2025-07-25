package com.sameer.zapurl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Public {
    @GetMapping("/public/{name}")
    public ResponseEntity<String> hello(@PathVariable String name) {
        return new ResponseEntity<>("greeting, "+name+"!", HttpStatus.OK);
    }
}
