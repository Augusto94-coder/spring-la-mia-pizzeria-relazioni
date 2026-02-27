package org.lessons.java.spring_pizzeria.controller;

import org.lessons.java.spring_pizzeria.model.Pizza;
import org.lessons.java.spring_pizzeria.model.PremiumDeal;
import org.lessons.java.spring_pizzeria.repository.PizzaRepository;
import org.lessons.java.spring_pizzeria.repository.PremiumDealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class PremiumDealController {

    @Autowired private PizzaRepository pizzaRepository;
    @Autowired private PremiumDealRepository dealRepository;

    @GetMapping("/pizzas/{pizzaId}/deals/create")
    public String create(@PathVariable Integer pizzaId, Model model) {
        Pizza pizza = pizzaRepository.findById(pizzaId).get();
        PremiumDeal deal = new PremiumDeal();
        deal.setPizza(pizza);

        model.addAttribute("deal", deal);
        return "deals/create";
    }

    @PostMapping("/pizzas/{pizzaId}/deals/create")
    public String store(@PathVariable Integer pizzaId,
                        @Valid @ModelAttribute("deal") PremiumDeal formDeal,
                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "deals/create";
        }

        // sicurezza: ri-attacco la pizza corretta
        Pizza pizza = pizzaRepository.findById(pizzaId).get();
        formDeal.setPizza(pizza);

        dealRepository.save(formDeal);
        return "redirect:/pizzas/" + pizzaId;
    }

    @GetMapping("/deals/edit/{dealId}")
    public String edit(@PathVariable Integer dealId, Model model) {
        model.addAttribute("deal", dealRepository.findById(dealId).get());
        return "deals/edit";
    }

    @PostMapping("/deals/edit/{dealId}")
    public String update(@PathVariable Integer dealId,
                         @Valid @ModelAttribute("deal") PremiumDeal formDeal,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "deals/edit";
        }

        // mantiene id
        formDeal.setId(dealId);

        Integer pizzaId = formDeal.getPizza().getId();
        dealRepository.save(formDeal);
        return "redirect:/pizzas/" + pizzaId;
    }
}