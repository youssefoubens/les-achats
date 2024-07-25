package net.youssef.gestion_achats.repository;



import net.youssef.gestion_achats.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Articlerepository extends JpaRepository<Article,Long>{
}

