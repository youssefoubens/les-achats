package net.youssef.gestion_achats.repository;



import net.youssef.gestion_achats.entity.consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<consultation,Long>{
}

