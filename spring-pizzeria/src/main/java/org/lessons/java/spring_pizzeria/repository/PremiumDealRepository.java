package org.lessons.java.spring_pizzeria.repository;

import org.lessons.java.spring_pizzeria.model.PremiumDeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PremiumDealRepository extends JpaRepository<PremiumDeal, Integer>{
    
}
