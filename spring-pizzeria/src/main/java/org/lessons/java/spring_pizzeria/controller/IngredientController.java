package org.lessons.java.spring_pizzeria.controller;

import java.util.List;

import org.lessons.java.spring_pizzeria.model.Ingredient;
import org.lessons.java.spring_pizzeria.model.Pizza;
import org.lessons.java.spring_pizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model) {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("ingredient", new Ingredient()); // per form creazione
        return "ingredients/index";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient,
                        BindingResult bindingResult,
                        Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "ingredients/index";
        }

        ingredientRepository.save(formIngredient);
        return "redirect:/ingredients";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Ingredient ingredientToDelete = ingredientRepository.findById(id).get();

        for ( Pizza linkedPizza : ingredientToDelete.getPizzas()){
            linkedPizza.getIngredients().remove(ingredientToDelete);
            
        }
        ingredientRepository.deleteById(id);
        return "redirect:/ingredients";
    }
}