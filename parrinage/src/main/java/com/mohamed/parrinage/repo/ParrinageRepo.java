package com.mohamed.parrinage.repo;

import com.mohamed.parrinage.model.Parrainage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParrinageRepo extends JpaRepository<Parrainage,Long> {
}
