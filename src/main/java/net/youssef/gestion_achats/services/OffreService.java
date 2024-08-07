package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.Article;
import net.youssef.gestion_achats.entity.offre;
import net.youssef.gestion_achats.entity.sarticle;
import net.youssef.gestion_achats.entity.ssarticle;
import net.youssef.gestion_achats.repository.Articlerepository;
import net.youssef.gestion_achats.repository.OffreRepository;
import net.youssef.gestion_achats.repository.SSarticleRepository;
import net.youssef.gestion_achats.repository.SarticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffreService {
    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private Articlerepository articleRepository;

    @Autowired
    private SarticleRepository sarticleRepository;

    @Autowired
    private SSarticleRepository sSarticleRepository;

    public List<offre> getAllOffres() {
        return offreRepository.findAll();
    }

    public offre getOffreById(Long id) {
        return offreRepository.findById(id).orElse(null);
    }

    public offre saveOffre(offre offre) {
        return offreRepository.save(offre);
    }

    public void deleteOffre(Long id) {
        offreRepository.deleteById(id);
    }

    public void updateArticlePrice(String article, double price) {
        // Logic to find the article by name and update its price
        Article foundArticle = articleRepository.findByName(article);
        if (foundArticle != null) {
            foundArticle.setPrice((float) price);
            articleRepository.save(foundArticle);
        }
    }

    public void updateSarticlePrice(String sarticle, double price) {
        // Logic to find the sarticle by name and update its price
        net.youssef.gestion_achats.entity.sarticle foundSarticle = sarticleRepository.findByName(sarticle);
        if (foundSarticle != null) {
            foundSarticle.setPrice((float) price);
            sarticleRepository.save(foundSarticle);
        }
    }

    public void updateSsarticlePrice(String ssarticle, double price) {
        // Logic to find the ssarticle by name and update its price
        net.youssef.gestion_achats.entity.ssarticle foundSsarticle = sSarticleRepository.findByName(ssarticle);
        if (foundSsarticle != null) {
            foundSsarticle.setPrice((float) price);
            sSarticleRepository.save(foundSsarticle);
        }
    }
}
