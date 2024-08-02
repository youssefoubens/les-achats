package net.youssef.gestion_achats.services;


import net.youssef.gestion_achats.entity.*;
import net.youssef.gestion_achats.entity.BORDEREAU;
import net.youssef.gestion_achats.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BordereauService {
    @Autowired
    private final BordereauRepository bordereauRepository;
    @Autowired
    private  Articlerepository articlerepository;
    @Autowired
    private Article_TypeRepository articleTypeRepository;
    @Autowired
    private SarticleRepository sarticleRepository;
    @Autowired
    private SSarticleRepository sSarticleRepository;
    @Autowired
    public BordereauService(BordereauRepository bordereauRepository) {
        this.bordereauRepository = bordereauRepository;
    }

    public List<BORDEREAU> getAllBordereaux() {
        return bordereauRepository.findAll();
    }

    public Optional<BORDEREAU> getBordereauById(Long id) {
        return bordereauRepository.findById(id);
    }

    public BORDEREAU saveBordereau(BORDEREAU bordereau) {
        return bordereauRepository.save(bordereau);
    }

    public void deleteBordereau(Long id) {
        bordereauRepository.deleteById(id);
    }

    // Additional business logic or custom methods can be added here
    @Transactional
    public void deleteBordereau(BORDEREAU bordereau) {
        // Fetch all articles related to the bordereau
        List<Article> articles = articlerepository.findByBordereau(bordereau);

        for (Article article : articles) {
            // Delete related sub-sub-articles, sub-articles, and article types

            sarticleRepository.deleteAllByArticle(article);
            articleTypeRepository.deleteAllByArticle(article);
        }


        // Delete all articles related to the bordereau
        articlerepository.deleteAll(articles);

        // Finally, delete the bordereau itself
        bordereauRepository.delete(bordereau);
    }

}
