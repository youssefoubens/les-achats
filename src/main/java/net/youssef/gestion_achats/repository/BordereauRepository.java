package net.youssef.gestion_achats.repository;

import net.youssef.gestion_achats.entity.BORDEREAU;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BordereauRepository extends JpaRepository<BORDEREAU, Long> {
    // Additional query methods (if needed) can be defined here
}
