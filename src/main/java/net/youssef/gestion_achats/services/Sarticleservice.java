package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.sarticle;
import net.youssef.gestion_achats.repository.SarticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Sarticleservice {
    @Autowired
    private SarticleRepository sarticleRepository;

    public List<sarticle> getAllSarticles() {
        return sarticleRepository.findAll();
    }

    public sarticle getSarticleById(Long id) {
        return sarticleRepository.findById(id).orElse(null);
    }

    public sarticle saveSarticle(sarticle sarticle) {
        return sarticleRepository.save(sarticle);
    }

    public void deleteSarticle(Long id) {
        sarticleRepository.deleteById(id);
    }
}
