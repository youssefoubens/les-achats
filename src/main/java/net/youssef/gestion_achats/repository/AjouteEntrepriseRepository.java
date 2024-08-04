package net.youssef.gestion_achats.repository;

import net.youssef.gestion_achats.entity.AjouteEntreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AjouteEntrepriseRepository extends JpaRepository<AjouteEntreprise, Long> {
    // You can define custom queries here if needed
}
