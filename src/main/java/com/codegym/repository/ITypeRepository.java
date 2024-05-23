package com.codegym.repository;

import com.codegym.model.Type;
import org.springframework.data.repository.CrudRepository;

public interface ITypeRepository extends CrudRepository<Type, Long> {
}
