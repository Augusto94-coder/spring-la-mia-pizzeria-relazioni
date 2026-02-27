package org.lessons.java.spring_pizzeria.repository;

import org.lessons.java.spring_pizzeria.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}