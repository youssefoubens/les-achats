package net.youssef.gestion_achats.repository;



import net.youssef.gestion_achats.entity.Article;
import net.youssef.gestion_achats.entity.Article_type;
import net.youssef.gestion_achats.entity.sarticle;
import net.youssef.gestion_achats.services.Sarticleservice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SarticleRepository extends JpaRepository<sarticle,Long>{
    sarticle findByName(String name);

   // List<sarticle> findByArticleType(Article_type articleType);
    void deleteAllByArticle(Article article);

    sarticle findByN(String nBrdVe);
}

