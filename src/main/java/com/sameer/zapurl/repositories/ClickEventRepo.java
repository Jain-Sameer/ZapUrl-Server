package com.sameer.zapurl.repositories;

import com.sameer.zapurl.models.ClickEvent;
import com.sameer.zapurl.models.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.LocalDateTime;

@Repository
public interface ClickEventRepo extends JpaRepository<ClickEvent, Long> {
    List<ClickEvent> findByUrlMappingAndClickDateBetween(UrlMapping urlMapping, LocalDateTime clickDateAfter, LocalDateTime clickDateBefore);

    List<ClickEvent> findByUrlMappingInAndClickDateBetween(List<UrlMapping> urlMappings, LocalDateTime clickDateAfter, LocalDateTime clickDateBefore);
}
