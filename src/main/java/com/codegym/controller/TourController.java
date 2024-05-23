package com.codegym.controller;

import com.codegym.model.Tour;
import com.codegym.model.Type;
import com.codegym.service.ITourService;
import com.codegym.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/tours")
public class TourController {

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITourService tourService;

    @ModelAttribute("types")
    public Iterable<Type> typeList() {
        return typeService.findAll();
    }

    @GetMapping
    public ModelAndView tourList(@RequestParam(defaultValue = "", required = false) String search, @PageableDefault(page = 0 , size = 2, sort = "destination") Pageable pageable) {
        Page<Tour> tourPage;
        if (!search.isEmpty()) {
            tourPage = tourService.findAllByDestinationContaining(pageable, search);
        } else {
            tourPage = tourService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/tour/list");
        modelAndView.addObject("tours", tourPage);
        modelAndView.addObject("search", search);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView listTourSearch(@RequestParam("search") Optional<String> search, Pageable pageable) {
        Page<Tour> tours;
        if (search.isPresent()) {
            tours = tourService.findAllByDestinationContaining(pageable, search.get());
        } else {
            tours = tourService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/tour/list");
        modelAndView.addObject("tours", tours);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/tour/create");
        modelAndView.addObject("tour", new Tour());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@Validated  @ModelAttribute("tour") Tour tour, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        new Tour().validate(tour, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/tour/create";
        }
        tourService.save(tour);
        redirectAttributes.addFlashAttribute("message", "Create new tour successfully");
        return "redirect:/tours";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Tour> tour = tourService.findById(id);
        if (tour.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/tour/update");
            modelAndView.addObject("tour", tour.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@Validated @ModelAttribute("tour") Tour tour, BindingResult bindingResult,
                         RedirectAttributes redirect) {
        new Tour().validate(tour, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/tour/update";
        } else {
            tourService.save(tour);
            redirect.addFlashAttribute("message", "Update tour successfully");
            return "redirect:/tours";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Long id,
                          RedirectAttributes redirect) {
        tourService.remove(id);
        redirect.addFlashAttribute("message", "Delete tour successfully");
        return "redirect:/tours";
    }
}
