package com.codegym.repository;

import com.codegym.model.Tour;
import com.codegym.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ITourRepository extends PagingAndSortingRepository<Tour, Long> {

    Iterable<Tour> findAllByType(Type type);

    Page<Tour> findAllByDestinationContaining(Pageable pageable, String name);


}
