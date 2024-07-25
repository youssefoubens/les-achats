package net.youssef.gestion_achats.repository;



import net.youssef.gestion_achats.entity.sarticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SarticleRepository extends JpaRepository<sarticle,Long>{
}

