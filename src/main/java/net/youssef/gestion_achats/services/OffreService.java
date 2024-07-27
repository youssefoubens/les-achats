package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.offre;
import net.youssef.gestion_achats.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffreService {
    @Autowired
    private OffreRepository offreRepository;

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


}
