package com.sameer.zapurl.service;

import com.sameer.zapurl.dtos.ClickEventDTO;
import com.sameer.zapurl.dtos.UrlMappingDTO;
import com.sameer.zapurl.exceptions.customexceptions.AlreadyExistsBackhalf;
import com.sameer.zapurl.exceptions.customexceptions.EmptyBackHalfException;
import com.sameer.zapurl.models.ClickEvent;
import com.sameer.zapurl.models.UrlMapping;
import com.sameer.zapurl.models.User;
import com.sameer.zapurl.repositories.ClickEventRepo;
import com.sameer.zapurl.repositories.UrlMappingRepo;
import com.sameer.zapurl.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UrlMappingService {
    @Autowired
    private UrlMappingRepo urlMappingRepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private ClickEventRepo clickEventRepo;

    public UrlMappingDTO createShortUrl(String originalUrl, User user, String backhalf) {
        if (backhalf == null || backhalf.isEmpty()) {
            throw new EmptyBackHalfException("Custom backhalf must not be empty.");
        }

        if (urlMappingRepo.existsByShortURL(backhalf)) {
            throw new AlreadyExistsBackhalf("Custom backhalf already in use.");
        }
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalURL(originalUrl);
        urlMapping.setUser(user);
        urlMapping.setDateTime(LocalDateTime.now());
        urlMapping.setShortURL(backhalf);

        urlMappingRepo.save(urlMapping);
        return modelMapper.map(urlMapping, UrlMappingDTO.class);
    }

//    private String generateShortUrl(String originalUrl) {
//        Random random = new Random();
//        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//        StringBuilder shortUrl = new StringBuilder(8);
//        for(int i = 0 ; i < 8;i++)  {
//            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
//        }
//        return shortUrl.toString();
//    }

    public List<UrlMappingDTO> getUrlsByUser(User user) {
        List<UrlMapping> urlMappingList = urlMappingRepo.findByUser(user);
        return urlMappingList.stream().map(urlMapping -> modelMapper.map(urlMapping, UrlMappingDTO.class)).toList();
    }

    public List<ClickEventDTO> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping urlMapping = urlMappingRepo.findByShortURL(shortUrl);
        if (urlMapping != null) {
            List<ClickEvent> byUrlMappingAndClickDateBetween = clickEventRepo.findByUrlMappingAndClickDateBetween(urlMapping, start, end);
            return byUrlMappingAndClickDateBetween.stream().collect(Collectors.groupingBy(clickEvent -> clickEvent.getClickDate().toLocalDate(), Collectors.counting()
            )).entrySet().stream().map(entry -> {
                ClickEventDTO clickEventDTO = new ClickEventDTO();
                clickEventDTO.setClickDate(entry.getKey());
                clickEventDTO.setCount(entry.getValue());
                return clickEventDTO;
            }).toList();
        }

        return null;
    }

    public Map<LocalDate, Long> getClickEventsByUserAndDate(User user, LocalDate start, LocalDate end) {
        List<UrlMapping> urlMappings = urlMappingRepo.findByUser(user);
        List<ClickEvent> clickEvents = clickEventRepo.findByUrlMappingInAndClickDateBetween(urlMappings, start.atStartOfDay(), end.plusDays(1).atStartOfDay());

        return clickEvents.stream().collect(Collectors.groupingBy(clickEvent -> clickEvent.getClickDate().toLocalDate(), Collectors.counting()));
    }

    public UrlMapping getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepo.findByShortURL(shortUrl);

        if(urlMapping!= null) {
            urlMapping.setClickCount(urlMapping.getClickCount()+1);
            urlMappingRepo.save(urlMapping);
            // record click event
            ClickEvent clickEvent = new ClickEvent();
            clickEvent.setClickDate(LocalDateTime.now());
            clickEvent.setUrlMapping(urlMapping);
            clickEventRepo.save(clickEvent);
        }
        return urlMapping;
    }
}
