package com.codegym.model;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.regex.Pattern;

@Entity
@Table(name = "Tour")
public class Tour implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String destination;


    @Column(nullable = false)
    private BigDecimal price;


    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    public Tour() {

    }

    public Tour(Long id, String code, String destination, BigDecimal price, Type type) {
        this.id = id;
        this.code = code;
        this.destination = destination;
        this.price = price;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Tour.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Tour tour = (Tour) target;

        if (tour.getCode() == null || tour.getCode().isEmpty()) {
            errors.rejectValue("code", "code.empty");
        } else if (!Pattern.matches("^CG.*$", tour.getCode()) || tour.getCode().length() < 8) {
            errors.rejectValue("code", "code.invalidFormat");
        }

        if (tour.getDestination() == null || tour.getDestination().isEmpty()) {
            errors.rejectValue("destination", "destination.empty");
        }

        if (tour.getPrice() == null || tour.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("price", "price.invalid");
        }

        if (tour.getType() == null) {
            errors.rejectValue("type", "type.null");
        }
    }

}
