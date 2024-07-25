package net.youssef.gestion_achats.services;

import net.youssef.gestion_achats.entity.Article_type;
import net.youssef.gestion_achats.repository.Article_TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class Articletypeservice {
    @Autowired
    private Article_TypeRepository articleTypeRepository;

    public List<Article_type> getAllTypes() {
        return articleTypeRepository.findAll();
    }

    public Article_type getTypeById(Long id) {
        return articleTypeRepository.findById(id).orElse(null);
    }

    public Article_type saveType(Article_type type) {
        return articleTypeRepository.save(type);
    }

    public void deleteType(Long id) {
        articleTypeRepository.deleteById(id);
    }
}
