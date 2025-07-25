package com.sameer.zapurl.controller;

import com.sameer.zapurl.dtos.ClickEventDTO;
import com.sameer.zapurl.dtos.UrlMappingDTO;
import com.sameer.zapurl.models.User;
import com.sameer.zapurl.service.UrlMappingService;
import com.sameer.zapurl.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@AllArgsConstructor
@RequestMapping("/api/urls")
@RestController
public class UrlMappingController {
    private UrlMappingService urlMappingService;
    private UserService userService;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDTO> createShortUrl(@RequestBody Map<String, String> request, Principal principal) {
        String originalUrl = request.get("originalUrl");
        User user = userService.findByUsername(principal.getName());
        UrlMappingDTO urlMappingDTO = urlMappingService.createShortUrl(originalUrl, user, request.get("backhalf"));
        return ResponseEntity.ok(urlMappingDTO);
    }

    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingDTO>> getUserUrls (Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<UrlMappingDTO> urlsByUser = urlMappingService.getUrlsByUser(user);
        return ResponseEntity.ok(urlsByUser);
    }


    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics(@PathVariable String shortUrl,
                                                               @RequestParam("startDate") String startDate,
                                                               @RequestParam("endDate") String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        List<ClickEventDTO> clickEventDTOS = urlMappingService.getClickEventsByDate(shortUrl, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        return ResponseEntity.ok(clickEventDTOS);

    }

    @GetMapping("/totalClicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(Principal principal,
                                                         @RequestParam("startDate") String startDate,
                                                         @RequestParam("endDate") String endDate) {
        User user = userService.findByUsername(principal.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        Map<LocalDate, Long> totalClicks = urlMappingService.getClickEventsByUserAndDate(user, start, end);
        return ResponseEntity.ok(totalClicks);

    }




}
