package com.codegym.controller;

import com.codegym.model.Tour;
import com.codegym.model.Type;
import com.codegym.service.ITourService;
import com.codegym.service.ITypeService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/types")
public class TypeController {

    @Autowired
    private ITypeService typeService;

    @Autowired
    private ITourService tourService;

    @GetMapping
    public ModelAndView typeList() {
        ModelAndView modelAndView = new ModelAndView("/type/list");
        Iterable<Type> types = typeService.findAll();
        modelAndView.addObject("types", types);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showFormCreate() {
        ModelAndView modelAndView = new ModelAndView("/type/create");
        modelAndView.addObject("type", new Type());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("type") Type type, BindingResult bindingResult,
                         RedirectAttributes attributes) {
        new Type().validate(type, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/type/create";
        } else {
            typeService.save(type);
            attributes.addFlashAttribute("message", "Create new type successfully");
            return "redirect:/types";
        }
    }

    @GetMapping("/update/{id}")
    public ModelAndView showFormUpdate(@PathVariable Long id) {
        Optional<Type> type = typeService.findById(id);
        if (type.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/type/update");
            modelAndView.addObject("type", type.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @PostMapping("/update/{id}")
    public String upadte(@Validated @ModelAttribute("type") Type type, BindingResult bindingResult,
                         RedirectAttributes attributes) {
        new Type().validate(type, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/type/update";
        } else {
            typeService.save(type);
            attributes.addFlashAttribute("message", "Update type successfully");
            return "redirect:/types";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {
        typeService.remove(id);
        redirect.addFlashAttribute("message", "Delete type successfully");
        return "redirect:/types";
    }

    @GetMapping("/view-type/{id}")
    public ModelAndView viewType(@PathVariable("id") long id) {
        Optional<Type> typeOptional = typeService.findById(id);

        if (typeOptional.isPresent()) {
            return new ModelAndView("/error_404");
        }
        Iterable<Tour> tours = tourService.findAllByType(typeOptional.get());

        ModelAndView  modelAndView = new ModelAndView("/tour/index");
        modelAndView.addObject("types", tours);
        return modelAndView;
    }
}
