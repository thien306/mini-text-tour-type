package com.codegym.service;

import com.codegym.model.Tour;
import com.codegym.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITourService extends IGenerateService<Tour> {

    Iterable<Tour> findAllByType(Type type);

    Page<Tour> findAll(Pageable pageable);

    Page<Tour> findAllByDestinationContaining(Pageable pageable, String name);


}
