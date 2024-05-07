package com.mohamed.parrinage.repo;

import com.mohamed.parrinage.model.Parrainage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParrinageRepo extends JpaRepository<Parrainage,Long> {

    List<Parrainage> findAllByParentIdAndParentEmail(Long parentId, String parentEmail);
}
