//package net.youssef.gestion_achats.services;
//
//import net.youssef.gestion_achats.entity.Famille;
//import net.youssef.gestion_achats.repository.FamilleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class FamilleService {
//
//    @Autowired
//    private FamilleRepository familleRepository;
//
//    @Transactional(readOnly = true)
//    public List<Famille> getAllFamilles() {
//        return familleRepository.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    public Famille getFamilleById(Long id) {
//        return familleRepository.findById(id).orElse(null);
//    }
//
//    @Transactional
//    public Famille saveFamille(Famille famille) {
//        return familleRepository.save(famille);
//    }
//
//    @Transactional
//    public void deleteFamille(Long id) {
//        familleRepository.deleteById(id);
//    }
//
//    @Transactional(readOnly = true)
//    public Famille findByName(String name) {
//        return familleRepository.findByName(name);
//    }
//}
