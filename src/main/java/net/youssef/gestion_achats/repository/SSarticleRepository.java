package net.youssef.gestion_achats.repository;



import net.youssef.gestion_achats.entity.Article;
import net.youssef.gestion_achats.entity.Article_type;
import net.youssef.gestion_achats.entity.sarticle;
import net.youssef.gestion_achats.entity.ssarticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SSarticleRepository extends JpaRepository<ssarticle,Long>{
    ssarticle findByName(String name);


    //ssarticle findByN(String nBrdVe);

    List<ssarticle> findByN(String nBrd);
}

