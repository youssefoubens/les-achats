package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.Article;
import net.youssef.gestion_achats.entity.Article_type;
import net.youssef.gestion_achats.repository.Article_TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Articletypeservice {

    @Autowired
    private Article_TypeRepository articleTypeRepository;

    @Transactional(readOnly = true)
    public List<Article_type> getAllTypes() {
        // Fetch all Article_types with an open transaction
        return articleTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Article_type getTypeById(Long id) {
        // Fetch Article_type by ID with an open transaction
        return articleTypeRepository.findById(id).orElse(null);
    }

    @Transactional
    public Article_type saveType(Article_type type) {
        // Save Article_type with an open transaction
        return articleTypeRepository.save(type);
    }

    @Transactional
    public void deleteType(Long id) {
        // Delete Article_type by ID with an open transaction
        articleTypeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Article_type findByName(String name) {
        // Fetch Article_type by name with an open transaction
        return articleTypeRepository.findByName(name);
    }



}
