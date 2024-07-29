package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.Fournisseur;
import net.youssef.gestion_achats.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FournisseurService {
    @Autowired
    private FournisseurRepository fournisseurRepository;


    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    public Fournisseur getFournisseurById(Long id) {
        return fournisseurRepository.findById(id).orElse(null);
    }

    public Fournisseur saveFournisseur(Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    public void deleteFournisseur(Long id) {
        fournisseurRepository.deleteById(id);
    }

    public Fournisseur findByName(String name) {
        return fournisseurRepository.findByName(name);
    }

    public List<Fournisseur> findAll() {
        return fournisseurRepository.findAll();
    }
}
