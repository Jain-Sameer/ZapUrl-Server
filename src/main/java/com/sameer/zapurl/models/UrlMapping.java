package com.sameer.zapurl.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "urlmappings")
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalURL;
    private String shortURL;
    private int clickCount;

    private LocalDateTime dateTime;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "urlMapping")
    private List<ClickEvent> clickEvents;
}
