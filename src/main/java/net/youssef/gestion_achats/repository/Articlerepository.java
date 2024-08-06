package net.youssef.gestion_achats.repository;



import net.youssef.gestion_achats.entity.Article;
import net.youssef.gestion_achats.entity.Article_type;
import net.youssef.gestion_achats.entity.BORDEREAU;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Articlerepository extends JpaRepository<Article,Long>{
    Article findByName(String name);

    List<Article> findByBordereau(BORDEREAU bordereau);

   // Optional<Object> findByN(String nBrdVe);

    List<Article> findByN(String nBrd);
}

