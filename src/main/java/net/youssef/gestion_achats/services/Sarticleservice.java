package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.Article_type;
import net.youssef.gestion_achats.entity.sarticle;
import net.youssef.gestion_achats.repository.SarticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Sarticleservice {

    @Autowired
    private SarticleRepository sarticleRepository;
    @Autowired
    private SarticleRepository sArticleRepository;


    @Transactional(readOnly = true)
    public List<sarticle> getAllSarticles() {
        // Fetch all sarticles with an open transaction
        return sarticleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public sarticle getSarticleById(Long id) {
        // Fetch sarticle by ID with an open transaction
        return sarticleRepository.findById(id).orElse(null);
    }

    @Transactional
    public sarticle saveSarticle(sarticle sarticle) {
        // Save sarticle with an open transaction
        return sarticleRepository.save(sarticle);
    }

    @Transactional
    public void deleteSarticle(Long id) {
        // Delete sarticle by ID with an open transaction
        sarticleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public sarticle findByName(String name) {
        // Fetch sarticle by name with an open transaction
        return sarticleRepository.findByName(name);
    }





}
