package net.youssef.gestion_achats.repository;



import net.youssef.gestion_achats.entity.Article;
import net.youssef.gestion_achats.entity.Article_type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Article_TypeRepository extends JpaRepository<Article_type,Long>{
    Article_type findByName(String name);
    List<Article_type> findByArticle(Article article);

    void deleteAllByArticle(Article article);
}

