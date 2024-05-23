package com.codegym.service.impl;

import com.codegym.model.Tour;
import com.codegym.model.Type;
import com.codegym.repository.ITourRepository;
import com.codegym.service.ITourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TourService implements ITourService {

    @Autowired
    private ITourRepository tourRepository;

    @Override
    public Iterable<Tour> findAllByType(Type type) {
        return tourRepository.findAllByType(type);
    }

    @Override
    public Page<Tour> findAll(Pageable pageable) {
        return tourRepository.findAll(pageable);
    }

    @Override
    public Page<Tour> findAllByDestinationContaining(Pageable pageable, String name) {
        return tourRepository.findAllByDestinationContaining(pageable, name);
    }

    @Override
    public Iterable<Tour> findAll() {
        return tourRepository.findAll();
    }

    @Override
    public void save(Tour tour) {
        tourRepository.save(tour);
    }

    @Override
    public Optional<Tour> findById(Long id) {
        return tourRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        tourRepository.deleteById(id);
    }
}
