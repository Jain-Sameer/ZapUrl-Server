package com.sameer.zapurl.repositories;

import com.sameer.zapurl.models.UrlMapping;
import com.sameer.zapurl.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlMappingRepo extends JpaRepository<UrlMapping, Long> {
    UrlMapping findByShortURL(String shortUrl);
    List<UrlMapping> findByUser(User user);
    Boolean existsByShortURL(String shortUrl);
}
