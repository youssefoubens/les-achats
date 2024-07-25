package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.ssarticle;
import net.youssef.gestion_achats.entity.ssarticle;
import net.youssef.gestion_achats.repository.SSarticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SSarticleservices {
    @Autowired
    private SSarticleRepository ssarticleRepository;

    public List<ssarticle> getAllSSarticles() {
        return ssarticleRepository.findAll();
    }

    public ssarticle getSSarticleById(Long id) {
        return ssarticleRepository.findById(id).orElse(null);
    }

    public ssarticle saveSSarticle(ssarticle ssarticle) {
        return ssarticleRepository.save(ssarticle);
    }

    public void deleteSSarticle(Long id) {
        ssarticleRepository.deleteById(id);
    }
}
