package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.dto.PublisherDto;
import org.example.librarymanagementsystem.model.Publisher;
import org.example.librarymanagementsystem.repo.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherDto create(PublisherDto dto) {
        if (publisherRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Publisher already exists");
        }

        Publisher publisher = Publisher.builder()
                .name(dto.getName())
                .build();

        Publisher saved = publisherRepository.save(publisher);
        dto.setId(saved.getId());
        return dto;
    }

    public List<PublisherDto> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(p -> {
                    PublisherDto dto = new PublisherDto();
                    dto.setId(p.getId());
                    dto.setName(p.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Publisher findEntityById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with ID: " + id));
    }
}
