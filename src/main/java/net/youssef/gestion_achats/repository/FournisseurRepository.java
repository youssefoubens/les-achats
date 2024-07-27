package net.youssef.gestion_achats.repository;



import net.youssef.gestion_achats.entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FournisseurRepository extends JpaRepository<Fournisseur,Long>{
    Fournisseur findByName(String name);
}

